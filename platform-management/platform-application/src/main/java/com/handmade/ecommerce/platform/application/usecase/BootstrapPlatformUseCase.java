package com.handmade.ecommerce.platform.application.usecase;

import com.handmade.ecommerce.platform.model.BrandIdentity;
import com.handmade.ecommerce.platform.model.ComplianceMandate;
import com.handmade.ecommerce.platform.model.CorporateIdentity;
import com.handmade.ecommerce.platform.model.LocalizationPolicy;
import com.handmade.ecommerce.platform.model.OperationalLimits;
import com.handmade.ecommerce.platform.model.PlatformOwner;
import com.handmade.ecommerce.platform.repository.PlatformOwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * One-Time Use Case to bootstrap the Platform Singleton.
 * Checks for existence before creating.
 */
@Service
public class BootstrapPlatformUseCase {

    private final PlatformOwnerRepository platformRepo;

    public BootstrapPlatformUseCase(PlatformOwnerRepository platformRepo) {
        this.platformRepo = platformRepo;
    }

    @Transactional
    public void execute(String platformName, 
                        BrandIdentity brand, 
                        CorporateIdentity corporate,
                        ComplianceMandate compliance, 
                        OperationalLimits limits,
                        LocalizationPolicy localization) {
        
        // 1. Check if platform exists
        if (platformRepo.get().isPresent()) {
            return; // Idempotent: already bootstrapped
        }

        // 2. Create using the Factory Command (Static Bootstrap Method)
        PlatformOwner owner = PlatformOwner.bootstrap(
            platformName,
            brand,
            corporate,
            compliance,
            limits,
            localization
        );

        // 3. Persist
        platformRepo.save(owner);
    }
}
