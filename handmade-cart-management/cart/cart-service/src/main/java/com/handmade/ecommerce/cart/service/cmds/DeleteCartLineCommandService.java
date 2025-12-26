package com.handmade.ecommerce.cart.service.cmds;

import com.handmade.ecommerce.cart.model.Cart;
import com.handmade.ecommerce.command.cart.DeleteCartLinePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Cart Command: Delete cart line
 */
@Component
public class DeleteCartLineCommandService extends BaseCartCommandService<DeleteCartLinePayload> {

    private static final Logger logger = LoggerFactory.getLogger(DeleteCartLineCommandService.class);

    @Override
    public void transitionTo(Cart cart, 
                           DeleteCartLinePayload payload, 
                           State startState, 
                           String eventId, 
                           State endState, 
                           STMInternalTransitionInvoker<?> stm, 
                           Transition transition) throws Exception {
        
        logger.info("Deleting cart line: cartId={}, cartLineId={}", 
            cart.getId(), payload.getOrderLineId());

        cartlineService.deleteCartItem(payload.getOrderLineId());
        
        // Recalculate cart total (common logic)
        recalculateCartTotal(cart);
        
        logger.info("Cart line deleted: cartLineId={}", payload.getOrderLineId());
    }
}
