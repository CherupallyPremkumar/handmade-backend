package com.handmade.ecommerce.selleronboardingcase.configuration.dao;

import com.handmade.ecommerce.onboarding.model.SellerOnboardingCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface SellerOnboardingCaseRepository extends JpaRepository<SellerOnboardingCase,String> {}
