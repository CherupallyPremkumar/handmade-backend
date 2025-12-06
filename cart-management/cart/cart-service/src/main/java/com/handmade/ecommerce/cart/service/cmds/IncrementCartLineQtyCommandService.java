package com.handmade.ecommerce.cart.service.cmds;

import com.handmade.ecommerce.cart.model.Cart;
import com.handmade.ecommerce.command.cart.CartLineIncrementQtyPayLoad;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Cart Command: Increment cart line quantity
 */
@Component
public class IncrementCartLineQtyCommandService extends BaseCartCommandService<CartLineIncrementQtyPayLoad> {

    private static final Logger logger = LoggerFactory.getLogger(IncrementCartLineQtyCommandService.class);

    @Override
    public void transitionTo(Cart cart, 
                           CartLineIncrementQtyPayLoad payload, 
                           State startState, 
                           String eventId, 
                           State endState, 
                           STMInternalTransitionInvoker<?> stm, 
                           Transition transition) throws Exception {
        
        logger.info("Incrementing cart line quantity: cartId={}, cartLineId={}, incrementBy={}", 
            cart.getId(), payload.getOrderLineId(), payload.getQuantity());

        cartlineService.incrementCartLine(payload);
        
        // Recalculate cart total (common logic)
        recalculateCartTotal(cart);
        
        logger.info("Cart line quantity incremented: cartLineId={}", payload.getOrderLineId());
    }
}
