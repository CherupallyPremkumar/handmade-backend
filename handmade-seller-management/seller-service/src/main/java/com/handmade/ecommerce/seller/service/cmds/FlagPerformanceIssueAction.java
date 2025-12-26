package com.handmade.ecommerce.seller.service.cmds;

import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.dto.command.FlagPerformanceIssuePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action: Flag performance issue
 * Transition: ACTIVE → AT_RISK
 * Triggered when seller performance metrics fall below thresholds
 * (ODR > 1%, LSR > 4%, etc.)
 */
public class FlagPerformanceIssueAction extends AbstractSTMTransitionAction<SellerAccount, FlagPerformanceIssuePayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(FlagPerformanceIssueAction.class);
    
    @Override
    public void doTransition(SellerAccount sellerAccount,
                            FlagPerformanceIssuePayload payload,
                            State startState,
                            String eventId,
                            State endState,
                            STMInternalTransitionInvoker<?> stm,
                            Transition transition) throws Exception {
        
        logger.warn("Flagging performance issue for seller: {}, issue: {}", 
                   sellerAccount.getId(), payload.getIssueType());
        
        // Update metrics with issue details
        if (sellerAccount.getMetrics() != null) {
            sellerAccount.getMetrics().setHasPerformanceIssues(true);
            sellerAccount.getMetrics().setLastPerformanceIssue(payload.getIssueType());
        }
        
        sellerAccount.setAtRisk(true);
        sellerAccount.setAtRiskReason(payload.getIssueType() + ": " + payload.getDescription());
        sellerAccount.setAtRiskSince(java.time.LocalDateTime.now());
        
        logger.warn("Seller {} moved to AT_RISK. Reason: {}", 
                   sellerAccount.getId(), payload.getDescription());
        
        // TODO: Send warning notification to seller
    }
}
