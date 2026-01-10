package com.handmade.ecommerce.promotion;

import com.handmade.ecommerce.promotion.entity.Coupon;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Coupon
 * Generated from entity file
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon, String> {
    
    Optional<Coupon> findByCouponCode(String couponCode);
    List<Coupon> findByPromotionId(String promotionId);
    List<Coupon> findByStatus(String status);
    List<Coupon> findByDiscountType(String discountType);
    List<Coupon> findByCurrencyCode(String currencyCode);
    List<Coupon> findByTargetCustomerId(String targetCustomerId);

    // Existence checks
    boolean existsByCouponCode(String couponCode);
}
