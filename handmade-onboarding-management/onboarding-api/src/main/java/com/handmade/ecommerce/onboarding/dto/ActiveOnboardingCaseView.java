package com.handmade.ecommerce.onboarding.dto;

import java.util.List;

/**
 * View object for active onboarding case lookup.
 * Used for idempotency checks.
 */
public class ActiveOnboardingCaseView {
    private String caseId;
    private String state;
    private List<String> nextAllowedActions;
    private boolean existing;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getNextAllowedActions() {
        return nextAllowedActions;
    }

    public void setNextAllowedActions(List<String> nextAllowedActions) {
        this.nextAllowedActions = nextAllowedActions;
    }

    public boolean isExisting() {
        return existing;
    }

    public void setExisting(boolean existing) {
        this.existing = existing;
    }
}
