package com.handmade.ecommerce.coupon.service.impl;

import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.coupon.configuration.dao.CouponDao;
import com.handmade.ecommerce.coupon.model.Coupon;
import com.handmade.ecommerce.coupon.model.DiscountType;
import com.handmade.ecommerce.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Implementation of CouponService
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponDao couponDao;

    @Override
    public Coupon validateCoupon(String couponCode, String userId) {
        Coupon coupon = couponDao.findByCode(couponCode)
            .orElseThrow(() -> new IllegalArgumentException("Coupon not found: " + couponCode));
        
        if (!coupon.isValid()) {
            throw new IllegalArgumentException("Coupon is not valid or has expired");
        }
        
        return coupon;
    }

    @Override
    public Money calculateDiscount(Coupon coupon, Money amount) {
        if (coupon.getDiscountType() == DiscountType.PERCENTAGE) {
            // Calculate percentage discount
            BigDecimal discountPercent = coupon.getDiscountValue();
            BigDecimal discountAmount = amount.getAmount()
                .multiply(discountPercent)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            
            Money discount = new Money(discountAmount, amount.getCurrency());
            
            // Apply max discount cap if set
            if (coupon.getMaxDiscountAmount() != null) {
                if (discount.getAmount().compareTo(coupon.getMaxDiscountAmount().getAmount()) > 0) {
                    return coupon.getMaxDiscountAmount();
                }
            }
            
            return discount;
        } else {
            // Fixed amount discount
            return new Money(coupon.getDiscountValue(), amount.getCurrency());
        }
    }

    @Override
    public boolean isApplicable(Coupon coupon, Money cartTotal) {
        // Check minimum order value
        if (coupon.getMinOrderValue() != null) {
            if (cartTotal.getAmount().compareTo(coupon.getMinOrderValue().getAmount()) < 0) {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public void markAsUsed(String couponCode) {
        Coupon coupon = couponDao.findByCode(couponCode)
            .orElseThrow(() -> new IllegalArgumentException("Coupon not found: " + couponCode));
        
        coupon.incrementUsage();
        couponDao.save(coupon);
    }

    @Override
    public Coupon getCouponByCode(String couponCode) {
        return couponDao.findByCode(couponCode).orElse(null);
    }
}
