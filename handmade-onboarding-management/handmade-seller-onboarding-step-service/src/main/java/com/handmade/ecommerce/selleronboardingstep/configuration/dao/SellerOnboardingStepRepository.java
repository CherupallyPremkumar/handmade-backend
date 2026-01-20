package com.handmade.ecommerce.selleronboardingstep.configuration.dao;

import com.handmade.ecommerce.onboarding.model.SellerOnboardingStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface SellerOnboardingStepRepository extends JpaRepository<SellerOnboardingStep,String> {}
