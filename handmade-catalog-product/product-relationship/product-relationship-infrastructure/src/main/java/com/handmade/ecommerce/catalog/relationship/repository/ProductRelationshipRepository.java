package com.handmade.ecommerce.catalog.relationship;

import com.handmade.ecommerce.catalog.relationship.entity.ProductRelationship;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ProductRelationship
 * Generated from entity file
 */
@Repository
public interface ProductRelationshipRepository extends JpaRepository<ProductRelationship, String> {
    
    List<ProductRelationship> findByParentId(String parentId);
    List<ProductRelationship> findByChildId(String childId);
    List<ProductRelationship> findByType(String type);
}
