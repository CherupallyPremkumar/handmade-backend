package com.handmade.ecommerce.seller.onboarding.persistence.repository;

import com.handmade.ecommerce.seller.onboarding.persistence.entity.SellerVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerVerificationJpaRepository extends JpaRepository<SellerVerificationEntity, String> {
    List<SellerVerificationEntity> findBySellerId(String sellerId);

    List<SellerVerificationEntity> findByVerificationType(String verificationType);

    List<SellerVerificationEntity> findByStatus(String status);

    List<SellerVerificationEntity> findByProviderVerificationId(String providerVerificationId);
}
