package com.handmade.ecommerce.onboarding.configuration.dao;

import com.handmade.ecommerce.onboarding.model.Onboarding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface OnboardingRepository extends JpaRepository<Onboarding,String> {}
