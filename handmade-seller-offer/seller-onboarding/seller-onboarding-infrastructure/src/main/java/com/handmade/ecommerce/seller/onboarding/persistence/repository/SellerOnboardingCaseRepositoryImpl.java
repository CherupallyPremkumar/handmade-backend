package com.handmade.ecommerce.seller.onboarding.persistence.repository;

import com.handmade.ecommerce.seller.onboarding.model.SellerOnboardingCase;
import com.handmade.ecommerce.seller.onboarding.repository.SellerOnboardingCaseRepository;
import com.handmade.ecommerce.seller.onboarding.persistence.mapper.SellerOnboardingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SellerOnboardingCaseRepositoryImpl implements SellerOnboardingCaseRepository {

    private final SellerOnboardingCaseJpaRepository jpaRepository;
    private final SellerOnboardingMapper mapper;

    @Autowired
    public SellerOnboardingCaseRepositoryImpl(SellerOnboardingCaseJpaRepository jpaRepository,
            SellerOnboardingMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<SellerOnboardingCase> findBySellerId(String sellerId) {
        return jpaRepository.findBySellerId(sellerId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SellerOnboardingCase> findByEmail(String email) {
        return jpaRepository.findByEmail(email).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SellerOnboardingCase> findByBusinessName(String businessName) {
        return jpaRepository.findByBusinessName(businessName).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SellerOnboardingCase> findByBusinessType(String businessType) {
        return jpaRepository.findByBusinessType(businessType).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SellerOnboardingCase> findByPhoneNumber(String phoneNumber) {
        return jpaRepository.findByPhoneNumber(phoneNumber).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SellerOnboardingCase> findByStateId(String stateId) {
        return jpaRepository.findByStateId(stateId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SellerOnboardingCase> findByFlowId(String flowId) {
        return jpaRepository.findByFlowId(flowId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SellerOnboardingCase> findByFlowIdAndStateId(String flowId, String stateId) {
        return jpaRepository.findByFlowIdAndStateId(flowId, stateId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
