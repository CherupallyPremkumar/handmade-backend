package com.handmade.ecommerce.seller.service.cmds;

import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.dto.command.RequestReinstatementPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action: Request reinstatement
 * Transition: SUSPENDED → PENDING_REINSTATEMENT
 * Seller appeals suspension and requests account reactivation
 */
public class RequestReinstatementAction extends AbstractSTMTransitionAction<SellerAccount, RequestReinstatementPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(RequestReinstatementAction.class);
    
    @Override
    public void doTransition(SellerAccount sellerAccount,
                            RequestReinstatementPayload payload,
                            State startState,
                            String eventId,
                            State endState,
                            STMInternalTransitionInvoker<?> stm,
                            Transition transition) throws Exception {
        
        logger.info("Reinstatement requested for suspended seller: {}", 
                   sellerAccount.getId());
        
        sellerAccount.setReinstatementRequested(true);
        sellerAccount.setReinstatementRequestedAt(java.time.LocalDateTime.now());
        sellerAccount.setReinstatementAppealText(payload.getAppealText());
        sellerAccount.setReinstatementPlanOfAction(payload.getPlanOfAction());
        
        logger.info("Seller {} moved to PENDING_REINSTATEMENT. Appeal submitted.", 
                   sellerAccount.getId());
        
        // TODO: Notify admin team for review
    }
}
