package com.handmade.ecommerce.coupon.configuration.dao;

import com.handmade.ecommerce.coupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO for Coupon entity
 */
@Repository
public interface CouponDao extends JpaRepository<Coupon, Long> {
    
    /**
     * Find coupon by code
     */
    Optional<Coupon> findByCode(String code);
    
    /**
     * Find active coupons by seller ID
     */
    java.util.List<Coupon> findBySellerIdAndActiveTrue(String sellerId);
    
    /**
     * Find active platform coupons
     */
    java.util.List<Coupon> findBySellerIdIsNullAndActiveTrue();
}
