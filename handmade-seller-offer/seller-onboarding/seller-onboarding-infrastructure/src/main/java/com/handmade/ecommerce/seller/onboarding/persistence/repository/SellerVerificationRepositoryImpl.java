package com.handmade.ecommerce.seller.onboarding.persistence.repository;

import com.handmade.ecommerce.seller.onboarding.model.SellerVerification;
import com.handmade.ecommerce.seller.onboarding.repository.SellerVerificationRepository;
import com.handmade.ecommerce.seller.onboarding.persistence.mapper.SellerOnboardingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SellerVerificationRepositoryImpl implements SellerVerificationRepository {

    private final SellerVerificationJpaRepository jpaRepository;
    private final SellerOnboardingMapper mapper;

    @Autowired
    public SellerVerificationRepositoryImpl(SellerVerificationJpaRepository jpaRepository,
            SellerOnboardingMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<SellerVerification> findBySellerId(String sellerId) {
        return jpaRepository.findBySellerId(sellerId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SellerVerification> findByVerificationType(String verificationType) {
        return jpaRepository.findByVerificationType(verificationType).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SellerVerification> findByStatus(String status) {
        return jpaRepository.findByStatus(status).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SellerVerification> findByProviderVerificationId(String providerVerificationId) {
        return jpaRepository.findByProviderVerificationId(providerVerificationId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
