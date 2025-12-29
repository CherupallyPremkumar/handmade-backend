package com.handmade.ecommerce.onboarding.service.impl;

import com.handmade.ecommerce.onboarding.api.OnboardingCaseService;
import com.handmade.ecommerce.onboarding.domain.aggregate.SellerOnboardingCase;
import com.handmade.ecommerce.onboarding.domain.enums.OnboardingEvents;
import com.handmade.ecommerce.identity.domain.model.VerificationResult;
import com.handmade.ecommerce.onboarding.dto.*;
import com.handmade.ecommerce.onboarding.api.OnBoardingInternalCaseService;
import com.handmade.ecommerce.onboarding.service.store.SellerOnboardingCaseStore;
import com.handmade.ecommerce.identity.api.IdentityVerificationService;
import com.handmade.ecommerce.identity.dto.IdentityVerificationView;
import com.handmade.ecommerce.identity.dto.IdentityDocumentRequest;

import org.chenile.base.exception.NotFoundException;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service implementation for SellerOnboardingCase
 */
public class OnboardingCaseServiceImpl extends StateEntityServiceImpl<SellerOnboardingCase>
        implements OnboardingCaseService, OnBoardingInternalCaseService {

    private final SellerOnboardingCaseStore<SellerOnboardingCase> entityStore;
    private final IdentityVerificationService identityService;

    public OnboardingCaseServiceImpl(STM<SellerOnboardingCase> stm,
            STMActionsInfoProvider stmActionsInfoProvider,
            SellerOnboardingCaseStore<SellerOnboardingCase> entityStore,
            IdentityVerificationService identityService) {
        super(stm, stmActionsInfoProvider, entityStore);
        this.entityStore = entityStore;
        this.identityService = identityService;
    }

    @Override
    public boolean existsByEmail(String email) {
        return entityStore.existsByEmail(email);
    }

    @Override
    public String createCase(CreateSellerOnboardingCaseRequest request) {
        return "";
    }

    @Override
    public Optional<ActiveOnboardingCaseView> findActiveCase(String email, String country) {
        return Optional.empty();
    }

    @Override
    public void lockPolicy(String caseId, LockPolicyRequest lockPolicyRequest) {

    }

    @Transactional
    public void updateBank(String caseId, BankUpdateRequest request) {

        SellerOnboardingCase c = entityStore.retrieve(caseId);

        if (c == null) {
            throw new NotFoundException(caseId);
        }

        // TODO: Implement updateBank method on SellerOnboardingCase
        // For now, this is a placeholder
        // The bank information should be stored in the BankVerificationSession
        // via the BankVerificationService, not directly on SellerOnboardingCase

        // c.updateBank(
        // request.getAccountHolderName(),
        // request.getAccountType(),
        // request.getRoutingNumber(),
        // request.getAccountNumber(),
        // request.getBankName(),
        // request.getCountry(),
        // request.getCurrency());

        // c.markBankProvided(); // boolean flag
    }

    public IdentityVerificationView submitIdentityDocuments(String caseId, IdentityDocumentRequest request) {
        return null;
    }

    public IdentityVerificationView getIdentityVerificationStatus(String caseId) {
        return null;
    }

    public TaxVerificationView submitTaxDocuments(String caseId, TaxDocumentRequest request) {
        return null;
    }

    public TaxVerificationView getTaxVerificationStatus(String caseId) {
        return null;
    }

    public BankVerificationView submitBankAccount(String caseId, BankAccountRequest request) {
        return null;
    }

    public BankVerificationView verifyMicroDeposits(String caseId, MicroDepositVerificationRequest request) {
        return null;
    }

    public BankVerificationView getBankVerificationStatus(String caseId) {
        return null;
    }

    @Override
    public void identityVerified(String onboardingCaseId, VerificationResult result) {
        processById(onboardingCaseId, OnboardingEvents.UPDATE_IDENTITY.name(), result);
    }

    /**
     * Creates a Stripe Identity verification session.
     * Returns client secret for frontend to open Stripe UI.
     * Does NOT trigger STM transition - webhook will do that.
     */
    @Override
    @Transactional
    public IdentityVerificationView createIdentitySession(String caseId) {
        // Get the onboarding case
        SellerOnboardingCase onboardingCase = entityStore.retrieve(caseId);
        if (onboardingCase == null) {
            throw new NotFoundException("Onboarding case not found: " + caseId);
        }

        // Delegate to identity service
        return identityService.createSession(caseId);
    }
}
