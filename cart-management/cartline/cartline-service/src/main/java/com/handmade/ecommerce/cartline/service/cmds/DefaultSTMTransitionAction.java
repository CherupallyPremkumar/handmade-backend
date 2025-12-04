package com.handmade.ecommerce.cartline.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.param.MinimalPayload;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.cartline.model.Cartline;

/**
    Use this class to do generic things that are relevant for all actions in the workflow
*/

public class DefaultSTMTransitionAction<PayloadType extends MinimalPayload>
    extends AbstractSTMTransitionAction<Cartline, PayloadType> {
    @Override
    public void transitionTo(Cartline cartline, PayloadType payload,
                 State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm,
                 Transition transition) {

    }
}