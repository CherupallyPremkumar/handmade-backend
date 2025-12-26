package com.handmade.ecommerce.seller.service.cmds;

import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.dto.command.ApproveReinstatementPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action: Approve reinstatement
 * Transition: PENDING_REINSTATEMENT → ACTIVE
 * Admin approves seller's appeal and reactivates account
 */
public class ApproveReinstatementAction extends AbstractSTMTransitionAction<SellerAccount, ApproveReinstatementPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(ApproveReinstatementAction.class);
    
    @Override
    public void doTransition(SellerAccount sellerAccount,
                            ApproveReinstatementPayload payload,
                            State startState,
                            String eventId,
                            State endState,
                            STMInternalTransitionInvoker<?> stm,
                            Transition transition) throws Exception {
        
        logger.info("Approving reinstatement for seller: {}, approved by: {}", 
                   sellerAccount.getId(), payload.getApprovedBy());
        
        // Clear suspension and reinstatement flags
        sellerAccount.setSuspended(false);
        sellerAccount.setSuspensionReason(null);
        sellerAccount.setReinstatementRequested(false);
        sellerAccount.setReinstatementApprovedAt(java.time.LocalDateTime.now());
        sellerAccount.setReinstatementApprovedBy(payload.getApprovedBy());
        
        // Reactivate account
        sellerAccount.setActive(true);
        
        logger.info("Reinstatement approved for seller {}. Account reactivated. Notes: {}", 
                   sellerAccount.getId(), payload.getApprovalNotes());
        
        // TODO: Send reactivation notification to seller
    }
}
