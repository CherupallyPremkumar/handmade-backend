package com.handmade.ecommerce.seller.onboarding.repository;

import com.handmade.ecommerce.seller.onboarding.entity.SellerOnboardingStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerOnboardingStepRepository extends JpaRepository<SellerOnboardingStep, String> {
    List<SellerOnboardingStep> findByOnboardingCaseId(String onboardingCaseId);

    List<SellerOnboardingStep> findByStepName(String stepName);

    List<SellerOnboardingStep> findByStatus(String status);
}
