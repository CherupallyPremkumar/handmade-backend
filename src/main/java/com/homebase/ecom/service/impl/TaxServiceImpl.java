package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.TaxRate;
import com.homebase.ecom.entitystore.TaxEntityStore;
import com.homebase.ecom.service.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class TaxServiceImpl implements TaxService {

    @Autowired
    TaxEntityStore taxEntityStore;

    public CartItem calculateTax(CartItem cartItem) {
        if (cartItem == null) return null;

        String country = cartItem.getCountry(); // assume CartItem has country
        String state = cartItem.getState();     // assume CartItem has state
        String productCategory = cartItem.getProductCategory();

        Date now = new Date();

        // Fetch the applicable tax rate
        TaxRate taxRate = taxEntityStore.findByCountryAndStateAndCategory(country, state, productCategory);

        BigDecimal rate = BigDecimal.ZERO;
        if (taxRate != null
                && (taxRate.getEffectiveFrom() == null || !now.before(taxRate.getEffectiveFrom()))
                && (taxRate.getEffectiveTo() == null || !now.after(taxRate.getEffectiveTo()))) {
            rate = taxRate.getRate() != null ? taxRate.getRate() : BigDecimal.ZERO;
        }

        // Calculate tax amount
        BigDecimal totalAmount = cartItem.getTotalAmount() != null ? cartItem.getTotalAmount() : BigDecimal.ZERO;
        BigDecimal taxAmount = totalAmount.multiply(rate);

        // Update CartItem
        cartItem.setTaxAmount(taxAmount);
        cartItem.setTotalAmount(totalAmount.add(taxAmount));

        return cartItem;
    }


}
