package com.handmade.ecommerce.cart.service.cmds;

import com.handmade.ecommerce.cart.model.Cart;
import com.handmade.ecommerce.command.cart.MergeCartPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Cart Command: Link guest cart to user after login
 * Simply updates the cart's userId - cartlines are already linked by cartId
 */
@Component
public class MergeCartCommandService extends BaseCartCommandService<MergeCartPayload> {

    private static final Logger logger = LoggerFactory.getLogger(MergeCartCommandService.class);

    @Override
    public void transitionTo(Cart cart, 
                           MergeCartPayload payload, 
                           State startState, 
                           String eventId, 
                           State endState, 
                           STMInternalTransitionInvoker<?> stm, 
                           Transition transition) throws Exception {
        
        logger.info("Linking guest cart to user: cartId={}, sessionId={}", 
            cart.getId(), payload.getSessionId());

        // Validate this is a guest cart (no userId yet)
        if (cart.getUserId() != null) {
            return;
        }
        
        // Validate sessionId matches
        if (!payload.getSessionId().equals(cart.getSessionId())) {
            throw new IllegalArgumentException("Session ID mismatch");
        }
        
        cartLineService.changeCart(cart.getId(), payload.getUserId());
      
        cart.setUserId(payload.getUserId());
        recalculateCartTotal(cart);
        // Link cart to user - cartlines are already linked by cartId
        // No need to touch cartlines module
        logger.info("Guest cart linked to user: cartId={}", cart.getId());
    }
}
