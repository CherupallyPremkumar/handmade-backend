package com.handmade.ecommerce.seller.onboarding.persistence.mapper;

import com.handmade.ecommerce.seller.onboarding.model.SellerOnboardingCase;
import com.handmade.ecommerce.seller.onboarding.model.SellerOnboardingStep;
import com.handmade.ecommerce.seller.onboarding.model.SellerVerification;
import com.handmade.ecommerce.seller.onboarding.persistence.entity.SellerOnboardingCaseEntity;
import com.handmade.ecommerce.seller.onboarding.persistence.entity.SellerOnboardingStepEntity;
import com.handmade.ecommerce.seller.onboarding.persistence.entity.SellerVerificationEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SellerOnboardingMapper {

    public SellerOnboardingCase toDomain(SellerOnboardingCaseEntity entity) {
        if (entity == null)
            return null;
        SellerOnboardingCase domain = SellerOnboardingCase.builder()
                .sellerId(entity.getSellerId())
                .email(entity.getEmail())
                .businessName(entity.getBusinessName())
                .businessType(entity.getBusinessType())
                .country(entity.getCountry())
                .contactPerson(entity.getContactPerson())
                .phoneNumber(entity.getPhoneNumber())
                .termsAccepted(entity.isTermsAccepted())
                .currentStep(entity.getCurrentStep())
                .completionPercentage(entity.getCompletionPercentage())
                .startedAt(entity.getStartedAt())
                .completedAt(entity.getCompletedAt())
                .approvedAt(entity.getApprovedAt())
                .approvedBy(entity.getApprovedBy())
                .rejectedAt(entity.getRejectedAt())
                .rejectedBy(entity.getRejectedBy())
                .rejectionReason(entity.getRejectionReason())
                .build();

        domain.setId(entity.getId());
        domain.setCurrentState(entity.getCurrentState());
        domain.setStateEntryTime(entity.getStateEntryTime());

        if (entity.getSteps() != null) {
            domain.setSteps(entity.getSteps().stream()
                    .map(this::toDomain)
                    .collect(Collectors.toList()));
            domain.getSteps().forEach(step -> step.setOnboardingCase(domain));
        }
        return domain;
    }

    public SellerOnboardingCaseEntity toEntity(SellerOnboardingCase domain) {
        if (domain == null)
            return null;
        SellerOnboardingCaseEntity entity = SellerOnboardingCaseEntity.builder()
                .sellerId(domain.getSellerId())
                .email(domain.getEmail())
                .businessName(domain.getBusinessName())
                .businessType(domain.getBusinessType())
                .country(domain.getCountry())
                .contactPerson(domain.getContactPerson())
                .phoneNumber(domain.getPhoneNumber())
                .termsAccepted(domain.isTermsAccepted())
                .currentStep(domain.getCurrentStep())
                .completionPercentage(domain.getCompletionPercentage())
                .startedAt(domain.getStartedAt())
                .completedAt(domain.getCompletedAt())
                .approvedAt(domain.getApprovedAt())
                .approvedBy(domain.getApprovedBy())
                .rejectedAt(domain.getRejectedAt())
                .rejectedBy(domain.getRejectedBy())
                .rejectionReason(domain.getRejectionReason())
                .build();

        entity.setId(domain.getId());
        entity.setCurrentState(domain.getCurrentState());
        entity.setStateEntryTime(domain.getStateEntryTime());

        if (domain.getSteps() != null) {
            entity.setSteps(domain.getSteps().stream()
                    .map(this::toEntity)
                    .collect(Collectors.toList()));
            entity.getSteps().forEach(step -> step.setOnboardingCase(entity));
        }
        return entity;
    }

    public SellerOnboardingStep toDomain(SellerOnboardingStepEntity entity) {
        if (entity == null)
            return null;
        SellerOnboardingStep domain = SellerOnboardingStep.builder()
                .stepName(entity.getStepName())
                .status(entity.getStatus())
                .providerRef(entity.getProviderRef())
                .lastUpdated(entity.getLastUpdated())
                .metadata(entity.getMetadata())
                .build();
        domain.setId(entity.getId());
        return domain;
    }

    public SellerOnboardingStepEntity toEntity(SellerOnboardingStep domain) {
        if (domain == null)
            return null;
        SellerOnboardingStepEntity entity = SellerOnboardingStepEntity.builder()
                .stepName(domain.getStepName())
                .status(domain.getStatus())
                .providerRef(domain.getProviderRef())
                .lastUpdated(domain.getLastUpdated())
                .metadata(domain.getMetadata())
                .build();
        entity.setId(domain.getId());
        return entity;
    }

    public SellerVerification toDomain(SellerVerificationEntity entity) {
        if (entity == null)
            return null;
        SellerVerification domain = SellerVerification.builder()
                .sellerId(entity.getSellerId())
                .verificationType(entity.getVerificationType())
                .status(entity.getStatus())
                .provider(entity.getProvider())
                .providerVerificationId(entity.getProviderVerificationId())
                .verificationUrl(entity.getVerificationUrl())
                .initiatedAt(entity.getInitiatedAt())
                .completedAt(entity.getCompletedAt())
                .expiresAt(entity.getExpiresAt())
                .verificationResult(entity.getVerificationResult())
                .verifiedBy(entity.getVerifiedBy())
                .rejectionReason(entity.getRejectionReason())
                .retryCount(entity.getRetryCount())
                .build();
        domain.setId(entity.getId());
        return domain;
    }

    public SellerVerificationEntity toEntity(SellerVerification domain) {
        if (domain == null)
            return null;
        SellerVerificationEntity entity = SellerVerificationEntity.builder()
                .sellerId(domain.getSellerId())
                .verificationType(domain.getVerificationType())
                .status(domain.getStatus())
                .provider(domain.getProvider())
                .providerVerificationId(domain.getProviderVerificationId())
                .verificationUrl(domain.getVerificationUrl())
                .initiatedAt(domain.getInitiatedAt())
                .completedAt(domain.getCompletedAt())
                .expiresAt(domain.getExpiresAt())
                .verificationResult(domain.getVerificationResult())
                .verifiedBy(domain.getVerifiedBy())
                .rejectionReason(domain.getRejectionReason())
                .retryCount(domain.getRetryCount())
                .build();
        entity.setId(domain.getId());
        return entity;
    }
}
