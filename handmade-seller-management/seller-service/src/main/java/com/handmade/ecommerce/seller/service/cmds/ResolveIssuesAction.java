package com.handmade.ecommerce.seller.service.cmds;

import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.dto.command.ResolveIssuesPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action: Resolve performance issues
 * Transition: AT_RISK → ACTIVE
 * Seller has improved metrics and resolved issues
 */
public class ResolveIssuesAction extends AbstractSTMTransitionAction<SellerAccount, ResolveIssuesPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(ResolveIssuesAction.class);
    
    @Override
    public void doTransition(SellerAccount sellerAccount,
                            ResolveIssuesPayload payload,
                            State startState,
                            String eventId,
                            State endState,
                            STMInternalTransitionInvoker<?> stm,
                            Transition transition) throws Exception {
        
        logger.info("Resolving performance issues for seller: {}, resolved by: {}", 
                   sellerAccount.getId(), payload.getResolvedBy());
        
        // Clear at-risk status
        sellerAccount.setAtRisk(false);
        sellerAccount.setAtRiskReason(null);
        sellerAccount.setAtRiskSince(null);
        
        // Update metrics
        if (sellerAccount.getMetrics() != null) {
            sellerAccount.getMetrics().setHasPerformanceIssues(false);
            sellerAccount.getMetrics().setLastPerformanceIssue(null);
        }
        
        logger.info("Performance issues resolved for seller {}. Returning to ACTIVE. Notes: {}", 
                   sellerAccount.getId(), payload.getResolutionNotes());
    }
}
