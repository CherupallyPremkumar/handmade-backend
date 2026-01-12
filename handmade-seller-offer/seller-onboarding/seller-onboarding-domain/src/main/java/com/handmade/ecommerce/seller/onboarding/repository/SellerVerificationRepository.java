package com.handmade.ecommerce.seller.onboarding.repository;

import com.handmade.ecommerce.seller.onboarding.model.SellerVerification;
import java.util.List;

/**
 * Pure Domain Repository interface for SellerVerification
 */
public interface SellerVerificationRepository {

    List<SellerVerification> findBySellerId(String sellerId);

    List<SellerVerification> findByVerificationType(String verificationType);

    List<SellerVerification> findByStatus(String status);

    List<SellerVerification> findByProviderVerificationId(String providerVerificationId);
}
