package com.handmade.ecommerce.platform.domain.repository;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import java.util.Optional;

/**
 * Repository for the Singleton PlatformOwner Aggregate.
 * Enforces singleton access semantics.
 */
public interface PlatformOwnerRepository {
    /**
     * Retrieves the single global platform instance.
     * @return Optional containing the platform if it has been bootstrapped.
     */
    Optional<PlatformOwner> get();

    /**
     * Persists the platform state.
     * @param owner The aggregate root.
     */
    void save(PlatformOwner owner);
}
