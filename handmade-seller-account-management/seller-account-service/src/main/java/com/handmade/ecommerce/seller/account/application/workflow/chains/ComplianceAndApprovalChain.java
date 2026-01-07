package com.handmade.ecommerce.seller.account.application.workflow.chains;

import com.handmade.ecommerce.seller.account.context.SellerOnboardingContext;

import org.chenile.owiz.impl.Chain;
import org.springframework.stereotype.Component;

/**
 * Chain 6: Compliance & Approval
 * State Transition: BANK_VERIFICATION → PENDING_APPROVAL → APPROVED/REJECTED
 * 
 * Steps:
 * 1. Run compliance check
 * 2. Transition to PENDING_APPROVAL
 * 3. Auto-approve or notify approvers
 * 4. Transition to APPROVED or REJECTED
 * 5. Log audit trail
 */
@Component("compliance-and-approval-chain")
public class ComplianceAndApprovalChain extends Chain<SellerOnboardingContext> {

    public ComplianceAndApprovalChain() {
        super();
    }
}
