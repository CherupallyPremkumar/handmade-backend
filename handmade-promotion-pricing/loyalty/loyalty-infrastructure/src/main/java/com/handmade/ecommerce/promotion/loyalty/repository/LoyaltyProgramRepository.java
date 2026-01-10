package com.handmade.ecommerce.promotion.loyalty;

import com.handmade.ecommerce.promotion.loyalty.entity.LoyaltyProgram;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for LoyaltyProgram
 * Generated from entity file
 */
@Repository
public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram, String> {
    
    List<LoyaltyProgram> findByProgramName(String programName);
    List<LoyaltyProgram> findByIsActive(Boolean isActive);
}
