package com.homebase.admin.strategy;

import com.homebase.admin.entity.Product;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal calculatePrice(Product product, int quantity);
}