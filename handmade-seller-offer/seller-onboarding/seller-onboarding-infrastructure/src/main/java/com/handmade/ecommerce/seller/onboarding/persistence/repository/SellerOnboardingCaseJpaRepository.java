package com.handmade.ecommerce.seller.onboarding.persistence.repository;

import com.handmade.ecommerce.seller.onboarding.persistence.entity.SellerOnboardingCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerOnboardingCaseJpaRepository extends JpaRepository<SellerOnboardingCaseEntity, String> {
    List<SellerOnboardingCaseEntity> findBySellerId(String sellerId);

    List<SellerOnboardingCaseEntity> findByEmail(String email);

    List<SellerOnboardingCaseEntity> findByBusinessName(String businessName);

    List<SellerOnboardingCaseEntity> findByBusinessType(String businessType);

    List<SellerOnboardingCaseEntity> findByPhoneNumber(String phoneNumber);

    @Query("SELECT e FROM SellerOnboardingCaseEntity e WHERE e.state.stateId = :stateId")
    List<SellerOnboardingCaseEntity> findByStateId(@Param("stateId") String stateId);

    @Query("SELECT e FROM SellerOnboardingCaseEntity e WHERE e.state.flowId = :flowId")
    List<SellerOnboardingCaseEntity> findByFlowId(@Param("flowId") String flowId);

    @Query("SELECT e FROM SellerOnboardingCaseEntity e WHERE e.state.flowId = :flowId AND e.state.stateId = :stateId")
    List<SellerOnboardingCaseEntity> findByFlowIdAndStateId(@Param("flowId") String flowId,
            @Param("stateId") String stateId);
}
