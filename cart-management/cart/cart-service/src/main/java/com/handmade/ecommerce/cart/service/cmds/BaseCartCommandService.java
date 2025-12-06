package com.handmade.ecommerce.cart.service.cmds;

import com.handmade.ecommerce.cart.model.Cart;
import com.handmade.ecommerce.cartline.model.Cartline;
import com.handmade.ecommerce.cartline.service.CartlineService;
import com.handmade.ecommerce.core.model.Money;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Base class for all Cart command services
 * Provides common logic for cart total calculation
 * 
 * Note: In a multi-seller marketplace, pricing is handled at the CARTLINE level
 * (each seller has their own pricing). The cart simply sums up all cartline totals.
 */
public abstract class BaseCartCommandService<T> extends AbstractSTMTransitionAction<Cart, T> {

    @Autowired
    protected CartlineService cartlineService;

    /**
     * Common logic: Recalculate cart total after any cart modification
     * Simply sums all cartline totals (pricing is done at cartline level)
     */
    protected void recalculateCartTotal(Cart cart) {
        // Get all cartlines for this cart
        List<Cartline> cartlines = cartlineService.getCartItems(cart.getId());
        
        // Sum all cartline totals
        Money total = sumCartlineTotals(cart, cartlines);
        
        // Update cart total
        cart.setTotalAmount(total);
    }

    /**
     * Sum all cartline totals
     * Each cartline has already been priced by its seller's pricing logic
     */
    private Money sumCartlineTotals(Cart cart, List<Cartline> cartlines) {
        if (cartlines.isEmpty()) {
            // Use cart's currency, or default to INR if not set
            String currency = (cart.getCurrency() != null) ? cart.getCurrency() : "INR";
            return new Money(java.math.BigDecimal.ZERO, currency);
        }
        
        // Sum all cartline totals
        return cartlines.stream()
            .map(Cartline::getTotalPrice)
            .reduce(Money::add)
            .orElseGet(() -> {
                // Use first cartline's currency as fallback
                String currency = cartlines.get(0).getCurrency();
                return new Money(java.math.BigDecimal.ZERO, currency);
            });
    }
    protected Money calculateSubtotal(Cart cart) {
        List<Cartline> cartlines = cartlineService.getCartItems(cart.getId());

        if (cartlines.isEmpty()) {
            String currency = (cart.getCurrency() != null) ? cart.getCurrency() : "INR";
            return new Money(java.math.BigDecimal.ZERO, currency);
        }

        return cartlines.stream()
                .map(Cartline::getTotalPrice)
                .reduce(Money::add)
                .orElseGet(() -> {
                    String currency = cartlines.get(0).getCurrency();
                    return new Money(java.math.BigDecimal.ZERO, currency);
                });
    }
}
