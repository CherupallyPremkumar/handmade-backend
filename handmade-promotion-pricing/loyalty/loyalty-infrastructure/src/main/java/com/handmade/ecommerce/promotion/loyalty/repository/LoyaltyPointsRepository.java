package com.handmade.ecommerce.promotion.loyalty;

import com.handmade.ecommerce.promotion.loyalty.entity.LoyaltyPoints;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for LoyaltyPoints
 * Generated from entity file
 */
@Repository
public interface LoyaltyPointsRepository extends JpaRepository<LoyaltyPoints, String> {
    
    Optional<LoyaltyPoints> findByCustomerId(String customerId);

    // Existence checks
    boolean existsByCustomerId(String customerId);
}
