package com.handmade.ecommerce.seller.onboarding.service.commands;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.chenile.owiz.impl.CommandBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.*;

/**
 * Amazon-Grade Base Command for all OWIZ Orchestrations.
 * Provides:
 * 1. MDC Logging (Traceability)
 * 2. Execution metrics (Observability via Micrometer)
 * 3. Standardized configuration access (Resiliency)
 * 4. Timeout Enforcement (Surround Pattern)
 */
public abstract class BaseOrchCommand<T extends OnboardingOrchContext<T>> extends CommandBase<T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final ExecutorService timeoutExecutor = Executors.newCachedThreadPool();

    @Autowired
    protected MeterRegistry meterRegistry;

    @Override
    public final void preprocess(T context) throws Exception {
        MDC.put("traceId", context.getTraceId());
        MDC.put("sellerId", context.getSellerId());
        MDC.put("command", getId());

        logger.info(">>> Starting Command: {} [Seller: {}]", getId(), context.getSellerId());
        super.preprocess(context);
    }

    @Override
    protected final void doExecute(T context) throws Exception {
        Timer.Sample sample = Timer.start(meterRegistry);
        int timeoutMs = getTimeoutMs();

        try {
            Future<?> future = timeoutExecutor.submit(() -> {
                try {
                    executeCommand(context);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            future.get(timeoutMs, TimeUnit.MILLISECONDS);

            sample.stop(Timer.builder("owiz.command.execution")
                    .tag("command", getId())
                    .tag("status", "success")
                    .register(meterRegistry));

        } catch (TimeoutException e) {
            sample.stop(Timer.builder("owiz.command.execution")
                    .tag("command", getId())
                    .tag("status", "timeout")
                    .register(meterRegistry));
            logger.error("Command {} timed out after {}ms", getId(), timeoutMs);
            throw new RuntimeException("Command timeout: " + getId(), e);
        } catch (ExecutionException e) {
            sample.stop(Timer.builder("owiz.command.execution")
                    .tag("command", getId())
                    .tag("status", "failure")
                    .register(meterRegistry));
            throw (Exception) e.getCause();
        }
    }

    /**
     * Actual business logic to be implemented by child commands.
     */
    protected abstract void executeCommand(T context) throws Exception;

    @Override
    public final void postprocess(T context) throws Exception {
        long duration = System.currentTimeMillis() - context.getStartTime();
        logger.info("<<< Completed Command: {} [Duration: {}ms]", getId(), duration);

        super.postprocess(context);
        MDC.clear();
    }

    protected int getTimeoutMs() {
        String timeout = getConfigValue("timeoutMs");
        return timeout != null ? Integer.parseInt(timeout) : 5000; // Default 5s
    }

    protected int getRetryLimit() {
        String limit = getConfigValue("retryLimit");
        return limit != null ? Integer.parseInt(limit) : 3;
    }
}
