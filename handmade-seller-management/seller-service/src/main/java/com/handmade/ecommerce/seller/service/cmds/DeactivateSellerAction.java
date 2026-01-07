package com.handmade.ecommerce.seller.service.cmds;

import com.handmade.ecommerce.seller.domain.aggregate.Seller;
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
public class DeactivateSellerAction extends AbstractSTMTransitionAction<Seller, DeactivateSellerPayload> {

    private static final Logger logger = LoggerFactory.getLogger(DeactivateSellerAction.class);

    @Override
    public void transitionTo(Seller seller,
            DeactivateSellerPayload payload,
            State startState,
            String eventId,
            State endState,
            STMInternalTransitionInvoker<?> stm,
            Transition transition) throws Exception {

        logger.warn("Deactivating seller store: {}, deactivated by: {}",
                seller.getId(), payload.getDeactivatedBy());

        // seller state is updated by STM automatically
        seller.setUpdatedAt(java.time.LocalDateTime.now());

        // TODO: Store reason in audit log or separate entity if needed
        logger.warn("Seller {} permanently DEACTIVATED. Reason: {}",
                seller.getId(), payload.getReason());

        // TODO: Trigger cleanup processes (close stores, settle payments, etc.)
    }
}
