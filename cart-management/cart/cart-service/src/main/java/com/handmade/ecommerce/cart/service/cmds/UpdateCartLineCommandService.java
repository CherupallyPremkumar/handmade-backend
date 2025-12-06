package com.handmade.ecommerce.cart.service.cmds;

import com.handmade.ecommerce.cart.model.Cart;
import com.handmade.ecommerce.command.cart.UpdateCartLinePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Cart Command: Update cart line quantity
 */
@Component
public class UpdateCartLineCommandService extends BaseCartCommandService<UpdateCartLinePayload> {

    private static final Logger logger = LoggerFactory.getLogger(UpdateCartLineCommandService.class);

    @Override
    public void transitionTo(Cart cart, 
                           UpdateCartLinePayload payload, 
                           State startState, 
                           String eventId, 
                           State endState, 
                           STMInternalTransitionInvoker<?> stm, 
                           Transition transition) throws Exception {
        
        logger.info("Updating cart line: cartId={}, cartLineId={}, quantity={}", 
            cart.getId(), payload.getOrderLineId(), payload.getQuantity());

        cartlineService.updateCartLine(payload);
        
        // Recalculate cart total (common logic)
        recalculateCartTotal(cart);
        
        logger.info("Cart line updated: cartLineId={}", payload.getOrderLineId());
    }
}
