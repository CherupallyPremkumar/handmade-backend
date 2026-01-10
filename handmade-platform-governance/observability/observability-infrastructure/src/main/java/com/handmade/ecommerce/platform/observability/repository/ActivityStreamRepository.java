package com.handmade.ecommerce.platform.observability;

import com.handmade.ecommerce.platform.observability.entity.ActivityStream;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ActivityStream
 * Generated from entity file
 */
@Repository
public interface ActivityStreamRepository extends JpaRepository<ActivityStream, String> {
    
    List<ActivityStream> findByActorId(String actorId);
    List<ActivityStream> findByActorType(String actorType);
    List<ActivityStream> findByEntityId(String entityId);
    List<ActivityStream> findByEntityType(String entityType);
}
