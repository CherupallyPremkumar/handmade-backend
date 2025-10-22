package com.homebase.ecom.service.cartitem;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.Price;
import com.homebase.ecom.entitystore.PriceEntityStore;
import com.homebase.ecom.service.PriceStateService;
import org.chenile.stm.action.STMAutomaticStateComputation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CheckIfCartItemOnSaleService implements STMAutomaticStateComputation<CartItem> {

    @Autowired
    private PriceStateService priceStateService;

    @Autowired
    TaxService taxService;

    @Override
    public String execute(CartItem cartItem) throws Exception {
        priceStateService.calculate(cartItem);
        if (price == null) {
            throw new IllegalStateException("Price not found for product variant: " + cartItem.getProductVariantId());
        }

        // Convert Double to BigDecimal safely
        BigDecimal originalPrice = BigDecimal.valueOf(price.getAmount());
        cartItem.setOriginalPrice(originalPrice);
        cartItem.setSnapshotPrice(originalPrice);

        BigDecimal totalAmount;
        // Check if the product is on sale
        return cartItem.isWasOnSale() ? "onSale" : "notOnSale";
    }
}