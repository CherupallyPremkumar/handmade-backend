package com.handmade.ecommerce.platform.infrastructure.persistence.jpa;

import com.handmade.ecommerce.platform.infrastructure.persistence.entity.PlatformOwnerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository for PlatformOwner.
 * Infrastructure layer.
 */
@Repository
public interface PlatformOwnerJpaRepository extends JpaRepository<PlatformOwnerJpaEntity, String> {
    // Spring Data will provide implementations
    // Custom queries can be added here if needed
}
