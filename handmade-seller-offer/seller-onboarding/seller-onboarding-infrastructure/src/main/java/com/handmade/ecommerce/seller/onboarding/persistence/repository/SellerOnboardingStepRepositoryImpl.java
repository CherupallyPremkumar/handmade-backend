package com.handmade.ecommerce.seller.onboarding.persistence.repository;

import com.handmade.ecommerce.seller.onboarding.model.SellerOnboardingStep;
import com.handmade.ecommerce.seller.onboarding.repository.SellerOnboardingStepRepository;
import com.handmade.ecommerce.seller.onboarding.persistence.mapper.SellerOnboardingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SellerOnboardingStepRepositoryImpl implements SellerOnboardingStepRepository {

    private final SellerOnboardingStepJpaRepository jpaRepository;
    private final SellerOnboardingMapper mapper;

    @Autowired
    public SellerOnboardingStepRepositoryImpl(SellerOnboardingStepJpaRepository jpaRepository,
            SellerOnboardingMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<SellerOnboardingStep> findByOnboardingCaseId(String onboardingCaseId) {
        // Assuming findByOnboardingCaseSellerId is the intended search by case id
        // (since case has sellerId)
        return jpaRepository.findByOnboardingCaseSellerId(onboardingCaseId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SellerOnboardingStep> findByStepName(String stepName) {
        return jpaRepository.findByStepName(stepName).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SellerOnboardingStep> findByStatus(String status) {
        return jpaRepository.findByStatus(status).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
