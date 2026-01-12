package com.handmade.ecommerce.seller.onboarding.persistence.repository;

import com.handmade.ecommerce.seller.onboarding.persistence.entity.SellerOnboardingStepEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerOnboardingStepJpaRepository extends JpaRepository<SellerOnboardingStepEntity, String> {
    List<SellerOnboardingStepEntity> findByOnboardingCaseSellerId(String sellerId);

    List<SellerOnboardingStepEntity> findByStepName(String stepName);

    List<SellerOnboardingStepEntity> findByStatus(String status);
}
