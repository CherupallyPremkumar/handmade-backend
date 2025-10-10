package com.homebase.admin.strategy.impl;

import com.homebase.admin.entity.Product;
import com.homebase.admin.strategy.PricingStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DefaultPricingStrategy implements PricingStrategy {
    @Override
    public BigDecimal calculatePrice(Product product, int quantity) {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
