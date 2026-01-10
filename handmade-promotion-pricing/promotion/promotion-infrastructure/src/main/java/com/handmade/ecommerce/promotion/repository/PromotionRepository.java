package com.handmade.ecommerce.promotion;

import com.handmade.ecommerce.promotion.entity.Promotion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Promotion
 * Generated from entity file
 */
@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {
    
    Optional<Promotion> findByPromotionCode(String promotionCode);
    List<Promotion> findByName(String name);
    List<Promotion> findByPromotionType(String promotionType);
    List<Promotion> findByDiscountType(String discountType);
    List<Promotion> findByCurrencyCode(String currencyCode);
    List<Promotion> findByStatus(String status);
    List<Promotion> findByTargetProductCategory(String targetProductCategory);
    List<Promotion> findByPriority(String priority);

    // Existence checks
    boolean existsByPromotionCode(String promotionCode);
}
