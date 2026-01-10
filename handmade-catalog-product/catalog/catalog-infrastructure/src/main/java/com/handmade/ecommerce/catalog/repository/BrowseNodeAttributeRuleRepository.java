package com.handmade.ecommerce.catalog;

import com.handmade.ecommerce.catalog.entity.BrowseNodeAttributeRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for BrowseNodeAttributeRule
 * Generated from entity file
 */
@Repository
public interface BrowseNodeAttributeRuleRepository extends JpaRepository<BrowseNodeAttributeRule, String> {
    
    List<BrowseNodeAttributeRule> findByBrowseNodeId(String browseNodeId);
    List<BrowseNodeAttributeRule> findByAttributeId(String attributeId);
}
