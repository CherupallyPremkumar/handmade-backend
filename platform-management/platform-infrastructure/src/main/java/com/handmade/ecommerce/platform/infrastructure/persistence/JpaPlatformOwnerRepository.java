package com.handmade.ecommerce.platform.infrastructure.persistence;

import com.handmade.ecommerce.platform.model.PlatformOwner;
import com.handmade.ecommerce.platform.repository.PlatformOwnerRepository;
import com.handmade.ecommerce.platform.infrastructure.messaging.DomainEventPublisher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Implementation of the Domain Repository.
 * Handles persistence and event dispatching.
 */
@Repository
public class JpaPlatformOwnerRepository implements PlatformOwnerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final EntityStore<PlatformOwner> entityStore;
    private final DomainEventPublisher eventPublisher;

    public JpaPlatformOwnerRepository(EntityStore<PlatformOwner> entityStore, DomainEventPublisher eventPublisher) {
        this.entityStore = entityStore;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<PlatformOwner> get() {
        return Optional.ofNullable(entityManager.find(PlatformOwner.class, PlatformOwner.SINGLETON_ID));
    }

    @Override
    public void save(PlatformOwner owner) {
        // Enforce Singleton ID just in case
        if (!PlatformOwner.SINGLETON_ID.equals(owner.getId())) {
             throw new IllegalStateException("PlatformOwner must have Singleton ID: " + PlatformOwner.SINGLETON_ID);
        }

        // Use Chenile EntityStore or generic JPA merge
        entityStore.store(owner);

        // Dispatch Events
        owner.getDomainEvents().forEach(eventPublisher::publish);
        owner.clearEvents();
    }
}
