package com.handmade.ecommerce.orchestrator.seller.dto;

/**
 * Step 8: Approval Payload
 * Input for seller approval/rejection
 */
public class ApprovalPayload {
    
    private String sellerId;
    private boolean compliancePassed;
    private boolean requiresManualApproval;
    private String approvalReason;
    private String rejectionReason;
    
    public ApprovalPayload() {
    }
    
    public String getSellerId() {
        return sellerId;
    }
    
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    
    public boolean isCompliancePassed() {
        return compliancePassed;
    }
    
    public void setCompliancePassed(boolean compliancePassed) {
        this.compliancePassed = compliancePassed;
    }
    
    public boolean isRequiresManualApproval() {
        return requiresManualApproval;
    }
    
    public void setRequiresManualApproval(boolean requiresManualApproval) {
        this.requiresManualApproval = requiresManualApproval;
    }
    
    public String getApprovalReason() {
        return approvalReason;
    }
    
    public void setApprovalReason(String approvalReason) {
        this.approvalReason = approvalReason;
    }
    
    public String getRejectionReason() {
        return rejectionReason;
    }
    
    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
