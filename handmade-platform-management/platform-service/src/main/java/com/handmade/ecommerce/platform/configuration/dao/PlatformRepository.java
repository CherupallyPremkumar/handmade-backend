package com.handmade.ecommerce.platform.configuration.dao;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository for PlatformOwner
 * Uses domain model directly (like chenile Process pattern)
 */
@Repository
public interface PlatformRepository extends JpaRepository<PlatformOwner, String> {
    
    /**
     * Find platform by name
     */
    Optional<PlatformOwner> findByName(String name);
    
    /**
     * Check if platform exists
     */
    boolean existsByName(String name);
}
