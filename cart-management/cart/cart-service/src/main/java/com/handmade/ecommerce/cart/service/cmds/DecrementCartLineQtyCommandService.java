package com.handmade.ecommerce.cart.service.cmds;

import com.handmade.ecommerce.cart.model.Cart;
import com.handmade.ecommerce.command.cart.CartLineDecrementQtyPayLoad;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Cart Command: Decrement cart line quantity
 */
@Component
public class DecrementCartLineQtyCommandService extends BaseCartCommandService<CartLineDecrementQtyPayLoad> {

    private static final Logger logger = LoggerFactory.getLogger(DecrementCartLineQtyCommandService.class);

    @Override
    public void transitionTo(Cart cart, 
                           CartLineDecrementQtyPayLoad payload, 
                           State startState, 
                           String eventId, 
                           State endState, 
                           STMInternalTransitionInvoker<?> stm, 
                           Transition transition) throws Exception {
        logger.info("Decrementing cart line quantity: cartId={}, cartLineId={}, decrementBy={}", 
            cart.getId(), payload.getOrderLineId(), payload.getQuantity());

        cartlineService.decrementCartLine(payload);
        
        // Recalculate cart total (common logic)
        recalculateCartTotal(cart);
        
        logger.info("Cart line quantity decremented: cartLineId={}", payload.getOrderLineId());
    }
}
