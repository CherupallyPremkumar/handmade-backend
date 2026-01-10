package com.handmade.ecommerce.payout.domain;

import com.handmade.ecommerce.payout.domain.entity.PayoutProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PayoutProfile
 * Generated from entity file
 */
@Repository
public interface PayoutProfileRepository extends JpaRepository<PayoutProfile, String> {
    
    Optional<PayoutProfile> findBySellerId(String sellerId);

    // Existence checks
    boolean existsBySellerId(String sellerId);
}
