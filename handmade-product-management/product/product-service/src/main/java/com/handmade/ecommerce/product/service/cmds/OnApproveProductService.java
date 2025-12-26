package com.handmade.ecommerce.product.service.cmds;

import com.handmade.ecommerce.product.dto.ApproveProductPaload;
import com.handmade.ecommerce.product.model.Product;
import org.chenile.stm.STM;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.springframework.stereotype.Component;


public class OnApproveProductService extends AbstractSTMTransitionAction<Product, ApproveProductPaload> {

    @Override
    public void transitionTo(Product stateEntity, ApproveProductPaload transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {

    }
}
