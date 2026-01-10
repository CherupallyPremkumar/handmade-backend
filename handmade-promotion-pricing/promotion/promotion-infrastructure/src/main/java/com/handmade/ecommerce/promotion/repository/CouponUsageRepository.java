package com.handmade.ecommerce.promotion;

import com.handmade.ecommerce.promotion.entity.CouponUsage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for CouponUsage
 * Generated from entity file
 */
@Repository
public interface CouponUsageRepository extends JpaRepository<CouponUsage, String> {
    
    List<CouponUsage> findByCouponId(String couponId);
    List<CouponUsage> findByCustomerId(String customerId);
    List<CouponUsage> findByOrderId(String orderId);
    List<CouponUsage> findByCurrencyCode(String currencyCode);
    List<CouponUsage> findByStatus(String status);
}
