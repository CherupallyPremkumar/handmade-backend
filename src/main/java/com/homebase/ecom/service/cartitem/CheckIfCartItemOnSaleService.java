package com.homebase.ecom.service.cartitem;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.Price;
import com.homebase.ecom.entitystore.PriceEntityStore;
import com.homebase.ecom.service.PriceStateService;
import com.homebase.ecom.service.TaxService;
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
        taxService.calculateTax(cartItem);
        return cartItem.isWasOnSale() ? "onSale" : "notOnSale";
    }
}