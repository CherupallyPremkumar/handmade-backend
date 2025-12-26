package com.handmade.ecommerce.seller.service.cmds;

import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.dto.command.DeactivateSellerPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action: Deactivate seller account
 * Transition: SUSPENDED → DEACTIVATED
 * Permanently close seller account
 */
public class DeactivateSellerAction extends AbstractSTMTransitionAction<SellerAccount, DeactivateSellerPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(DeactivateSellerAction.class);
    
    @Override
    public void doTransition(SellerAccount sellerAccount,
                            DeactivateSellerPayload payload,
                            State startState,
                            String eventId,
                            State endState,
                            STMInternalTransitionInvoker<?> stm,
                            Transition transition) throws Exception {
        
        logger.warn("Deactivating seller account: {}, deactivated by: {}", 
                   sellerAccount.getId(), payload.getDeactivatedBy());
        
        sellerAccount.setActive(false);
        sellerAccount.setSuspended(false);
        sellerAccount.setDeactivated(true);
        sellerAccount.setDeactivatedAt(java.time.LocalDateTime.now());
        sellerAccount.setDeactivatedBy(payload.getDeactivatedBy());
        sellerAccount.setDeactivationReason(payload.getReason());
        
        logger.warn("Seller {} permanently DEACTIVATED. Reason: {}", 
                   sellerAccount.getId(), payload.getReason());
        
        // TODO: Trigger cleanup processes (close stores, settle payments, etc.)
    }
}
