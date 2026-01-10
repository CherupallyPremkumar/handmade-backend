package com.handmade.ecommerce.catalog;

import com.handmade.ecommerce.catalog.entity.AttributeDefinition;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for AttributeDefinition
 * Generated from entity file
 */
@Repository
public interface AttributeDefinitionRepository extends JpaRepository<AttributeDefinition, String> {
    
    Optional<AttributeDefinition> findByCode(String code);
    List<AttributeDefinition> findByName(String name);
    List<AttributeDefinition> findByType(String type);

    // Existence checks
    boolean existsByCode(String code);
}
