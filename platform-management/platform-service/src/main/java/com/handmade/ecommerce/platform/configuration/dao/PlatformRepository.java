package com.handmade.ecommerce.platform.configuration.dao;

import com.handmade.ecommerce.platform.infrastructure.persistence.entity.PlatformOwnerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository for PlatformOwner persistence
 * Uses PlatformOwnerJpaEntity (not domain model)
 */
@Repository
public interface PlatformRepository extends JpaRepository<PlatformOwnerJpaEntity, String> {
    
    /**
     * Find platform by name
     */
    Optional<PlatformOwnerJpaEntity> findByName(String name);
    
    /**
     * Check if platform exists
     */
    boolean existsByName(String name);
}
