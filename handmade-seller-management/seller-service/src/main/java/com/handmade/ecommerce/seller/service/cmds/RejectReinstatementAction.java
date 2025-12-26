package com.handmade.ecommerce.seller.service.cmds;

import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.dto.command.RejectReinstatementPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action: Reject reinstatement
 * Transition: PENDING_REINSTATEMENT → DEACTIVATED
 * Admin rejects seller's appeal, account permanently closed
 */
public class RejectReinstatementAction extends AbstractSTMTransitionAction<SellerAccount, RejectReinstatementPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(RejectReinstatementAction.class);
    
    @Override
    public void doTransition(SellerAccount sellerAccount,
                            RejectReinstatementPayload payload,
                            State startState,
                            String eventId,
                            State endState,
                            STMInternalTransitionInvoker<?> stm,
                            Transition transition) throws Exception {
        
        logger.info("Rejecting reinstatement for seller: {}, rejected by: {}", 
                   sellerAccount.getId(), payload.getRejectedBy());
        
        sellerAccount.setReinstatementRequested(false);
        sellerAccount.setReinstatementRejectedAt(java.time.LocalDateTime.now());
        sellerAccount.setReinstatementRejectedBy(payload.getRejectedBy());
        sellerAccount.setReinstatementRejectionReason(payload.getRejectionReason());
        
        // Permanently deactivate
        sellerAccount.setActive(false);
        sellerAccount.setDeactivated(true);
        sellerAccount.setDeactivatedAt(java.time.LocalDateTime.now());
        
        logger.warn("Reinstatement rejected for seller {}. Account DEACTIVATED. Reason: {}", 
                   sellerAccount.getId(), payload.getRejectionReason());
        
        // TODO: Send final deactivation notification to seller
    }
}
