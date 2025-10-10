package com.homebase.admin.strategy.impl;

import com.homebase.admin.entity.Product;
import com.homebase.admin.strategy.PricingStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SalePricingStrategy implements PricingStrategy {
    @Override
    public BigDecimal calculatePrice(Product product, int quantity) {
        BigDecimal price = product.isOnSale() ? product.getSalePrice() : product.getPrice();
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
