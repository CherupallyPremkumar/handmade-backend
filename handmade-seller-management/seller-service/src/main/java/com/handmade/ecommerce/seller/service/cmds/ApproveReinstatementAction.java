package com.handmade.ecommerce.seller.service.cmds;

import com.handmade.ecommerce.seller.domain.aggregate.Seller;
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
public class ApproveReinstatementAction extends AbstractSTMTransitionAction<Seller, ApproveReinstatementPayload> {

    private static final Logger logger = LoggerFactory.getLogger(ApproveReinstatementAction.class);

    @Override
    public void transitionTo(Seller seller,
            ApproveReinstatementPayload payload,
            State startState,
            String eventId,
            State endState,
            STMInternalTransitionInvoker<?> stm,
            Transition transition) throws Exception {

        logger.info("Approving reinstatement for seller: {}, approved by: {}",
                seller.getId(), payload.getApprovedBy());

        // seller state is updated by STM automatically to ACTIVE
        seller.setUpdatedAt(java.time.LocalDateTime.now());

        logger.info("Reinstatement approved for seller {}. Account reactivated. Notes: {}",
                seller.getId(), payload.getApprovalNotes());

        // TODO: Send reactivation notification to seller
    }
}
