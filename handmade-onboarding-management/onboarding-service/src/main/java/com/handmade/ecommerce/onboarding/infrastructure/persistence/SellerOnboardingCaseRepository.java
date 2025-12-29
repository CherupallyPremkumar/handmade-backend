package com.handmade.ecommerce.onboarding.infrastructure.persistence;

import com.handmade.ecommerce.onboarding.domain.aggregate.SellerOnboardingCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerOnboardingCase (Onboarding)
 */
@Repository
public interface SellerOnboardingCaseRepository extends JpaRepository<SellerOnboardingCase, String> {
    boolean existsByEmail(String email);

    SellerOnboardingCase findByEmail(String email);
}
