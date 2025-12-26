package com.handmade.ecommerce.cart.service.cmds;

import com.handmade.ecommerce.cart.model.Cart;
import com.handmade.ecommerce.cartline.model.Cartline;
import com.handmade.ecommerce.command.cart.RemoveCouponPayload;
import com.handmade.ecommerce.core.model.Money;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Cart Command: Remove coupon from cart
 */
@Component
public class RemoveCouponCommandService extends BaseCartCommandService<RemoveCouponPayload> {

    private static final Logger logger = LoggerFactory.getLogger(RemoveCouponCommandService.class);

    @Override
    public void transitionTo(Cart cart, 
                           RemoveCouponPayload payload, 
                           State startState, 
                           String eventId, 
                           State endState, 
                           STMInternalTransitionInvoker<?> stm, 
                           Transition transition) throws Exception {
        logger.info("Removing coupon from cart: cartId={}, currentCoupon={}", 
            cart.getId(), cart.getCouponCode());
        cart.setCouponCode(null);
        cart.setPlatformDiscount(null);
        cart.setSubtotal(null);
        // 3. Recalculate total
        recalculateCartTotal(cart);
        logger.info("Coupon removed successfully: cartId={}", cart.getId());
    }
}
