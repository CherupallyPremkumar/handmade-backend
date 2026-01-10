package com.handmade.ecommerce.seller.offer;

import com.handmade.ecommerce.seller.offer.entity.ShippingRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ShippingRule
 * Generated from entity file
 */
@Repository
public interface ShippingRuleRepository extends JpaRepository<ShippingRule, String> {
    
    List<ShippingRule> findByTemplateId(String templateId);
}
