package com.handmade.ecommerce.cart.service.cmds;

import com.handmade.ecommerce.cart.model.Cart;
import com.handmade.ecommerce.cartline.model.Cartline;
import com.handmade.ecommerce.cartline.service.CartlineService;
import com.handmade.ecommerce.command.cart.ApplyCouponPayload;
import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.coupon.model.Coupon;
import com.handmade.ecommerce.coupon.model.CouponType;
import com.handmade.ecommerce.coupon.service.CouponService;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Cart Command: Apply coupon to cart
 */
@Component
public class ApplyCouponCommandService extends BaseCartCommandService<ApplyCouponPayload> {

    private static final Logger logger = LoggerFactory.getLogger(ApplyCouponCommandService.class);

    @Autowired
    private CouponService couponService;

    @Autowired
    private CartlineService cartlineService;

    @Override
    public void transitionTo(Cart cart, 
                           ApplyCouponPayload payload, 
                           State startState, 
                           String eventId, 
                           State endState, 
                           STMInternalTransitionInvoker<?> stm, 
                           Transition transition) throws Exception {
        
        logger.info("Applying coupon to cart: cartId={}, couponCode={}", 
            cart.getId(), payload.getCouponCode());
        // 1. Validate coupon
        Coupon coupon = couponService.validateCoupon(
            payload.getCouponCode(), 
            cart.getUserId()
        );
        
        // 2. Check if applicable
        Money cartSubtotal = calculateSubtotal(cart);
        if (!couponService.isApplicable(coupon, cartSubtotal)) {
            throw new IllegalArgumentException("Coupon not applicable to this cart");
        }
        
        // 3. Apply based on type
        if (coupon.getType() == CouponType.PLATFORM) {
            applyPlatformCoupon(cart, coupon, cartSubtotal);
        }
        
        // 4. Recalculate total
        recalculateCartTotal(cart);
        logger.info("Coupon applied successfully: cartId={}, discount={}", 
            cart.getId(), cart.getPlatformDiscount());
    }

    /**
     * Apply platform-wide coupon to cart
     */
    private void applyPlatformCoupon(Cart cart, Coupon coupon, Money subtotal) {
        Money discount = couponService.calculateDiscount(coupon, subtotal);
        cart.setCouponCode(coupon.getCode());
        cart.setPlatformDiscount(discount);
        cart.setSubtotal(subtotal);
        logger.debug("Platform coupon applied: code={}, discount={}", 
            coupon.getCode(), discount);
    }
    /**
     * Calculate cart subtotal (sum of all line totals)
     */

}
