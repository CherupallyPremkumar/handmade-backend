package com.handmade.ecommerce.platform.application.usecase;

import com.handmade.ecommerce.platform.model.PlatformOwner;
import com.handmade.ecommerce.platform.repository.PlatformOwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Operational Use Case to activate the pending Platform.
 */
@Service
public class ActivatePlatformUseCase {

    private final PlatformOwnerRepository platformRepo;

    public ActivatePlatformUseCase(PlatformOwnerRepository platformRepo) {
        this.platformRepo = platformRepo;
    }

    @Transactional
    public void execute(String operatorId) {
        // 1. Retrieve Singleton
        PlatformOwner owner = platformRepo.get()
            .orElseThrow(() -> new IllegalStateException("Platform has not been bootstrapped. Run BootstrapUseCase first."));

        // 2. Delegate to Domain Logic
        owner.activate(operatorId);

        // 3. Persist Changes (Update State + Save Events)
        platformRepo.save(owner);
    }
}
