package com.handmade.ecommerce.promotion;

import com.handmade.ecommerce.promotion.entity.DiscountApplication;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for DiscountApplication
 * Generated from entity file
 */
@Repository
public interface DiscountApplicationRepository extends JpaRepository<DiscountApplication, String> {
    
    List<DiscountApplication> findByOrderId(String orderId);
    List<DiscountApplication> findByOrderLineId(String orderLineId);
    List<DiscountApplication> findByPromotionId(String promotionId);
    List<DiscountApplication> findByCouponId(String couponId);
    List<DiscountApplication> findByDiscountType(String discountType);
    List<DiscountApplication> findByCurrencyCode(String currencyCode);
}
