package com.handmade.ecommerce.paymentexecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * PaymentExecutorFactory
 * 
 * Factory for getting the appropriate PaymentExecutor implementation based on PSP type.
 * Uses Spring's dependency injection to automatically discover all PaymentExecutor implementations.
 */
@Component
public class PaymentExecutorFactory {
    
    private final Map<String, PaymentExecutor> executorMap;
    
    /**
     * Constructor that auto-wires all PaymentExecutor implementations.
     * Spring will inject all beans that implement PaymentExecutor interface.
     */
    @Autowired
    public PaymentExecutorFactory(List<PaymentExecutor> executors) {
        this.executorMap = executors.stream()
                .collect(Collectors.toMap(
                        PaymentExecutor::getPspType,
                        Function.identity()
                ));
    }
    
    /**
     * Get PaymentExecutor for the specified PSP type.
     * 
     * @param pspType PSP type (e.g., "RAZORPAY", "STRIPE", "PAYPAL")
     * @return PaymentExecutor implementation for the PSP
     * @throws PaymentExecutorException if no executor found for PSP type
     */
    public PaymentExecutor getExecutor(String pspType) {
        PaymentExecutor executor = executorMap.get(pspType.toUpperCase());
        
        if (executor == null) {
            throw new PaymentExecutorException(
                    "No PaymentExecutor found for PSP type: " + pspType,
                    pspType,
                    "EXECUTOR_NOT_FOUND"
            );
        }
        
        return executor;
    }
    public String determinePspType(String currency) {
        if ("INR".equals(currency)) {
            return "RAZORPAY"; // India
        } else {
            return "STRIPE"; // International
        }
    }
    /**
     * Check if an executor exists for the given PSP type.
     * 
     * @param pspType PSP type
     * @return true if executor exists, false otherwise
     */
    public boolean hasExecutor(String pspType) {
        return executorMap.containsKey(pspType.toUpperCase());
    }
    
    /**
     * Get all supported PSP types.
     * 
     * @return Set of supported PSP types
     */
    public java.util.Set<String> getSupportedPspTypes() {
        return executorMap.keySet();
    }
}
