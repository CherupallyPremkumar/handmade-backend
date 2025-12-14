package com.handmade.ecommerce.cart.service.cmds;

import com.handmade.ecommerce.cart.model.Cart;
import com.handmade.ecommerce.command.cart.AddShipmentPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

public class AddShipmentCommandService extends BaseCartCommandService<AddShipmentPayload> {
    @Override
    public void transitionTo(Cart stateEntity, AddShipmentPayload transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {

    }
}
