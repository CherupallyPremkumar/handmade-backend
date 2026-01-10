package com.handmade.ecommerce.seller.offer;

import com.handmade.ecommerce.seller.offer.entity.ShippingTemplate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ShippingTemplate
 * Generated from entity file
 */
@Repository
public interface ShippingTemplateRepository extends JpaRepository<ShippingTemplate, String> {
    
    List<ShippingTemplate> findByPlatformId(String platformId);
    List<ShippingTemplate> findBySellerId(String sellerId);
    List<ShippingTemplate> findByName(String name);
}
