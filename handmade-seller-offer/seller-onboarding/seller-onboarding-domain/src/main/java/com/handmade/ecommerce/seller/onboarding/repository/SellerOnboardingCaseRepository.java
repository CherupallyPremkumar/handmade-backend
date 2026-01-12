package com.handmade.ecommerce.seller.onboarding.repository;

import com.handmade.ecommerce.seller.onboarding.model.SellerOnboardingCase;
import java.util.List;

/**
 * Pure Domain Repository interface for SellerOnboardingCase
 */
public interface SellerOnboardingCaseRepository {

    List<SellerOnboardingCase> findBySellerId(String sellerId);

    List<SellerOnboardingCase> findByEmail(String email);

    List<SellerOnboardingCase> findByBusinessName(String businessName);

    List<SellerOnboardingCase> findByBusinessType(String businessType);

    List<SellerOnboardingCase> findByPhoneNumber(String phoneNumber);

    // STM State queries
    List<SellerOnboardingCase> findByStateId(String stateId);

    List<SellerOnboardingCase> findByFlowId(String flowId);

    List<SellerOnboardingCase> findByFlowIdAndStateId(String flowId, String stateId);
}
