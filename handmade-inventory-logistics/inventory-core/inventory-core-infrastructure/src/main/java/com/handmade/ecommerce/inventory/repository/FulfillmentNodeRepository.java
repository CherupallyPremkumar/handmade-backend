package com.handmade.ecommerce.inventory;

import com.handmade.ecommerce.inventory.entity.FulfillmentNode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for FulfillmentNode
 * Generated from entity file
 */
@Repository
public interface FulfillmentNodeRepository extends JpaRepository<FulfillmentNode, String> {
    
    List<FulfillmentNode> findByPlatformId(String platformId);
    List<FulfillmentNode> findByName(String name);
    List<FulfillmentNode> findByType(String type);
    List<FulfillmentNode> findByStateCode(String stateCode);
    List<FulfillmentNode> findByCountryCode(String countryCode);
    List<FulfillmentNode> findByPostalCode(String postalCode);
}
