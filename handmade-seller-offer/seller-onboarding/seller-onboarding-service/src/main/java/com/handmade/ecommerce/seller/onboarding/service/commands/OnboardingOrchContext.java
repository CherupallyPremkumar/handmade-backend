package com.handmade.ecommerce.seller.onboarding.service.commands;

import org.chenile.owiz.impl.ChainContext;
import org.chenile.owiz.impl.ChainContextContainer;
import org.chenile.base.response.WarningAware;
import org.chenile.base.response.ResponseMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Base Context for Seller Onboarding Orchestration.
 */
public abstract class OnboardingOrchContext<T extends OnboardingOrchContext<T>> 
    implements ChainContextContainer<T>, WarningAware {
    
    private String sellerId;
    private String traceId;
    private String onboardingCaseId;
    private long startTime;
    private Map<String, Object> results = new HashMap<>();
    private List<ResponseMessage> warningMessages = new ArrayList<>();
    private ChainContext<T> chainContext;

    public OnboardingOrchContext(String sellerId, String onboardingCaseId) {
        this.sellerId = sellerId;
        this.onboardingCaseId = onboardingCaseId;
        this.traceId = UUID.randomUUID().toString();
        this.startTime = System.currentTimeMillis();
    }

    public String getOnboardingCaseId() {
        return onboardingCaseId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getTraceId() {
        return traceId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void addResult(String key, Object value) {
        results.put(key, value);
    }

    public Object getResult(String key) {
        return results.get(key);
    }

    @Override
    public ChainContext<T> getChainContext() {
        return chainContext;
    }

    @Override
    public void setChainContext(ChainContext<T> chainContext) {
        this.chainContext = chainContext;
    }

    @Override
    public List<ResponseMessage> getWarningMessages() {
        return warningMessages;
    }

    @Override
    public void addWarningMessage(ResponseMessage m) {
        this.warningMessages.add(m);
    }

    @Override
    public void removeAllWarnings() {
        this.warningMessages.clear();
    }
}
