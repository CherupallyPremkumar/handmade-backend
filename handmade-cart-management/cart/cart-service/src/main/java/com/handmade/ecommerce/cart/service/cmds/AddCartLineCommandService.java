package com.handmade.ecommerce.cart.service.cmds;

import com.handmade.ecommerce.cart.model.Cart;
import com.handmade.ecommerce.command.cart.AddCartLinePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Cart Command: Add item to cart
 * Delegates to CartlineService which handles pricing, inventory, tax
 */
@Component
public class AddCartLineCommandService extends BaseCartCommandService<AddCartLinePayload> {

    private static final Logger logger = LoggerFactory.getLogger(AddCartLineCommandService.class);

    @Override
    public void transitionTo(Cart cart, 
                           AddCartLinePayload payload, 
                           State startState, 
                           String eventId, 
                           State endState, 
                           STMInternalTransitionInvoker<?> stm, 
                           Transition transition) throws Exception {
        
        logger.info("Adding item to cart: cartId={}, variantId={}, quantity={}", 
            cart.getId(), payload.getVariantId(), payload.getQuantity());

        // 1. Delegate to CartlineService to create cartline
        cartlineService.createCartLine(payload);
        
        // 2. Recalculate cart total (common logic from base class)
        recalculateCartTotal(cart);
        
        logger.info("Cart updated: cartId={}, totalAmount={}", 
            cart.getId(), cart.getTotalAmount());
    }
}
