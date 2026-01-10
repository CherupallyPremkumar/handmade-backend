package com.handmade.ecommerce.seller;

import com.handmade.ecommerce.seller.entity.SellerConfiguration;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerConfiguration
 * Generated from entity file
 */
@Repository
public interface SellerConfigurationRepository extends JpaRepository<SellerConfiguration, String> {
    
    List<SellerConfiguration> findBySellerId(String sellerId);
    List<SellerConfiguration> findByDefaultWarehouseId(String defaultWarehouseId);
}
