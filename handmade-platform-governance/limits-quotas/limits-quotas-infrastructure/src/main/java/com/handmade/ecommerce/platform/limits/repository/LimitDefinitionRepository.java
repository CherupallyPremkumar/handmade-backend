package com.handmade.ecommerce.platform.limits;

import com.handmade.ecommerce.platform.limits.entity.LimitDefinition;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for LimitDefinition
 * Generated from entity file
 */
@Repository
public interface LimitDefinitionRepository extends JpaRepository<LimitDefinition, String> {
    
    Optional<LimitDefinition> findByLimitKey(String limitKey);
    List<LimitDefinition> findByResourceType(String resourceType);

    // Existence checks
    boolean existsByLimitKey(String limitKey);
}
