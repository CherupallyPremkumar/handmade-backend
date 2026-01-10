package com.handmade.ecommerce.seller.onboarding.repository;

import com.handmade.ecommerce.seller.onboarding.entity.SellerOnboardingCase;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerOnboardingCase
 * Generated from entity file
 */
@Repository
public interface SellerOnboardingCaseRepository extends JpaRepository<SellerOnboardingCase, String> {
    
    List<SellerOnboardingCase> findBySellerId(String sellerId);
    List<SellerOnboardingCase> findByEmail(String email);
    List<SellerOnboardingCase> findByBusinessName(String businessName);
    List<SellerOnboardingCase> findByBusinessType(String businessType);
    List<SellerOnboardingCase> findByPhoneNumber(String phoneNumber);

    // STM State queries
    @Query("SELECT e FROM SellerOnboardingCase e WHERE e.state.stateId = :stateId")
    List<SellerOnboardingCase> findByStateId(@Param("stateId") String stateId);

    @Query("SELECT e FROM SellerOnboardingCase e WHERE e.state.flowId = :flowId")
    List<SellerOnboardingCase> findByFlowId(@Param("flowId") String flowId);

    @Query("SELECT e FROM SellerOnboardingCase e WHERE e.state.flowId = :flowId AND e.state.stateId = :stateId")
    List<SellerOnboardingCase> findByFlowIdAndStateId(@Param("flowId") String flowId, @Param("stateId") String stateId);
}
