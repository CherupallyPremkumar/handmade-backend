package com.handmade.ecommerce.platform.application.service;

import com.handmade.ecommerce.platform.application.command.*;
import com.handmade.ecommerce.platform.application.exception.PlatformAccessDeniedException;
import com.handmade.ecommerce.platform.application.port.in.PlatformGovernanceUseCase;
import com.handmade.ecommerce.platform.application.port.out.AuthorizationService;
import com.handmade.ecommerce.platform.application.port.out.DomainEventPublisher;
import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.domain.entity.CommissionPolicy;
import com.handmade.ecommerce.platform.domain.repository.PlatformOwnerRepository;
import com.handmade.ecommerce.platform.domain.valueobject.BrandIdentity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application Service implementing Platform Governance use cases.
 * 
 * RESPONSIBILITIES:
 * - Orchestrate domain operations
 * - Manage transactions
 * - Enforce authorization
 * - Publish domain events
 * 
 * DOES NOT:
 * - Contain business logic (domain owns that)
 * - Access database directly (uses repository)
 * - Expose REST endpoints (infrastructure layer does that)
 */
@Service
@Transactional
public class PlatformGovernanceService implements PlatformGovernanceUseCase {

    private final PlatformOwnerRepository repository;
    private final DomainEventPublisher eventPublisher;
    private final AuthorizationService authorizationService;

    public PlatformGovernanceService(PlatformOwnerRepository repository,
                                    DomainEventPublisher eventPublisher,
                                    AuthorizationService authorizationService) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.authorizationService = authorizationService;
    }

    @Override
    public void activatePlatform(ActivatePlatformCommand command) {
        // 1. Authorization check
        checkPlatformAdminPermission(command.getOperatorId(), "ACTIVATE_PLATFORM");

        // 2. Load aggregate
        PlatformOwner platform = repository.get()
            .orElseThrow(() -> new IllegalStateException("Platform not bootstrapped"));

        // 3. Execute domain logic
        platform.activate(command.getOperatorId());

        // 4. Persist changes
        repository.save(platform);

        // 5. Publish domain events
        publishDomainEvents(platform);
    }

    @Override
    public void reviseCommission(ReviseCommissionCommand command) {
        // 1. Authorization check
        checkPlatformAdminPermission(command.getOperatorId(), "REVISE_COMMISSION");

        // 2. Load aggregate
        PlatformOwner platform = repository.get()
            .orElseThrow(() -> new IllegalStateException("Platform not found"));

        // 3. Create new commission policy (domain entity)
        CommissionPolicy newPolicy = new CommissionPolicy(
            platform.getId(),
            command.getNewTakeRate(),
            command.getFlatFee(),
            command.getCurrency()
        );
        newPolicy.setChangeReason(command.getReason());

        // 4. Execute domain logic
        platform.reviseCommissionStructure(newPolicy, command.getOperatorId());

        // 5. Persist changes
        repository.save(platform);

        // 6. Publish domain events
        publishDomainEvents(platform);
    }

    @Override
    public void lockPlatform(LockPlatformCommand command) {
        // 1. Authorization check
        checkPlatformAdminPermission(command.getOperatorId(), "LOCK_PLATFORM");

        // 2. Load aggregate
        PlatformOwner platform = repository.get()
            .orElseThrow(() -> new IllegalStateException("Platform not found"));

        // 3. Execute domain logic
        platform.imposeSanctions(command.getReason());

        // 4. Persist changes
        repository.save(platform);

        // 5. Publish domain events
        publishDomainEvents(platform);
    }

    @Override
    public void updateBrandIdentity(UpdateBrandIdentityCommand command) {
        // 1. Authorization check
        checkPlatformAdminPermission(command.getOperatorId(), "UPDATE_BRAND");

        // 2. Load aggregate
        PlatformOwner platform = repository.get()
            .orElseThrow(() -> new IllegalStateException("Platform not found"));

        // 3. Create new brand identity (value object)
        BrandIdentity newBrand = new BrandIdentity(
            command.getLogoUrl(),
            command.getPrimaryColor(),
            command.getSupportEmail()
        );

        // 4. Execute domain logic
        platform.rebrand(newBrand);

        // 5. Persist changes
        repository.save(platform);

        // 6. Publish domain events (if any)
        publishDomainEvents(platform);
    }

    @Override
    public void publishLegalTerms(PublishLegalTermsCommand command) {
        // 1. Authorization check
        checkPlatformAdminPermission(command.getOperatorId(), "PUBLISH_LEGAL_TERMS");

        // 2. Load aggregate
        PlatformOwner platform = repository.get()
            .orElseThrow(() -> new IllegalStateException("Platform not found"));

        // 3. Execute domain logic
        platform.publishLegalTerms(command.getTermsUrl(), command.getPrivacyUrl());

        // 4. Persist changes
        repository.save(platform);

        // 5. Publish domain events
        publishDomainEvents(platform);
    }

    /**
     * Check if operator has platform admin permission.
     * Throws PlatformAccessDeniedException if not authorized.
     */
    private void checkPlatformAdminPermission(String operatorId, String operation) {
        if (!authorizationService.isPlatformAdmin(operatorId)) {
            throw new PlatformAccessDeniedException(operatorId, operation);
        }
    }

    /**
     * Publish all domain events emitted by the aggregate.
     * This is orchestration logic - belongs in application layer.
     */
    private void publishDomainEvents(PlatformOwner platform) {
        platform.getDomainEvents().forEach(eventPublisher::publish);
        platform.clearEvents();
    }
}
