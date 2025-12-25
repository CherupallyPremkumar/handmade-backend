package com.handmade.ecommerce.platform.infrastructure.persistence.repository;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.domain.repository.PlatformOwnerRepository;
import com.handmade.ecommerce.platform.infrastructure.persistence.entity.PlatformOwnerJpaEntity;
import com.handmade.ecommerce.platform.infrastructure.persistence.jpa.PlatformOwnerJpaRepository;
import com.handmade.ecommerce.platform.infrastructure.persistence.mapper.PlatformOwnerMapper;
import com.handmade.ecommerce.platform.application.port.out.DomainEventPublisher;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * JPA implementation of PlatformOwnerRepository.
 * Infrastructure layer - implements domain repository interface.
 * Adapts Spring Data JPA to domain repository contract.
 */
@Repository
@Transactional
public class JpaPlatformOwnerRepository implements PlatformOwnerRepository {

    private final PlatformOwnerJpaRepository jpaRepository;
    private final DomainEventPublisher eventPublisher;

    public JpaPlatformOwnerRepository(PlatformOwnerJpaRepository jpaRepository,
                                     DomainEventPublisher eventPublisher) {
        this.jpaRepository = jpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<PlatformOwner> get() {
        // PlatformOwner is a singleton with SINGLETON_ID
        return jpaRepository.findById(PlatformOwner.SINGLETON_ID)
                .map(PlatformOwnerMapper::toDomain);
    }

    @Override
    public void save(PlatformOwner platformOwner) {
        if (platformOwner == null) {
            throw new IllegalArgumentException("PlatformOwner cannot be null");
        }

        // Convert domain to JPA entity
        PlatformOwnerJpaEntity entity = PlatformOwnerMapper.toEntity(platformOwner);
        
        // Save to database
        jpaRepository.save(entity);
        
        // Publish domain events AFTER successful persistence
        platformOwner.getDomainEvents().forEach(eventPublisher::publish);
        platformOwner.clearEvents();
    }
}
