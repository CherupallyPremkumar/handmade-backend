package com.handmade.ecommerce.seller.onboarding.repository;

import com.handmade.ecommerce.seller.onboarding.entity.SellerVerification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerVerification
 * Generated from entity file
 */
@Repository
public interface SellerVerificationRepository extends JpaRepository<SellerVerification, String> {

    List<SellerVerification> findBySellerId(String sellerId);

    List<SellerVerification> findByVerificationType(String verificationType);

    List<SellerVerification> findByStatus(String status);

    List<SellerVerification> findByProviderVerificationId(String providerVerificationId);
}
