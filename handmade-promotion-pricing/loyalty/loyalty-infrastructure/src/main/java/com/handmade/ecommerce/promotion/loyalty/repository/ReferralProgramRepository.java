package com.handmade.ecommerce.promotion.loyalty;

import com.handmade.ecommerce.promotion.loyalty.entity.ReferralProgram;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ReferralProgram
 * Generated from entity file
 */
@Repository
public interface ReferralProgramRepository extends JpaRepository<ReferralProgram, String> {
    
    List<ReferralProgram> findByReferrerId(String referrerId);
    Optional<ReferralProgram> findByReferralCode(String referralCode);
    List<ReferralProgram> findByReferralStatus(String referralStatus);

    // Existence checks
    boolean existsByReferralCode(String referralCode);
}
