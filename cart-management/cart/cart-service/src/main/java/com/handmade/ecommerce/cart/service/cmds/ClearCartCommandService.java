package com.handmade.ecommerce.cart.service.cmds;

import com.handmade.ecommerce.cart.model.Cart;
import com.handmade.ecommerce.cartline.model.Cartline;
import com.handmade.ecommerce.cartline.service.CartlineService;
import com.handmade.ecommerce.command.cart.ClearCartPayload;
import com.handmade.ecommerce.core.model.Money;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Cart Command: Clear all items from cart
 */
@Component
public class ClearCartCommandService extends BaseCartCommandService<ClearCartPayload> {

    private static final Logger logger = LoggerFactory.getLogger(ClearCartCommandService.class);

    @Autowired
    private CartlineService cartlineService;

    @Override
    public void transitionTo(Cart cart, 
                           ClearCartPayload payload, 
                           State startState, 
                           String eventId, 
                           State endState, 
                           STMInternalTransitionInvoker<?> stm, 
                           Transition transition) throws Exception {

        cartlineService.deleteCartLines(cart.getId());
        recalculateCartTotal(cart);

        logger.info("Cart cleared successfully: cartId={}", cart.getId());
    }

  
}
