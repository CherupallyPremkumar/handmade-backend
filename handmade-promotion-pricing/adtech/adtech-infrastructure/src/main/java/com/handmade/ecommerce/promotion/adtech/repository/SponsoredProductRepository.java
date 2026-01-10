package com.handmade.ecommerce.promotion.adtech;

import com.handmade.ecommerce.promotion.adtech.entity.SponsoredProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SponsoredProduct
 * Generated from entity file
 */
@Repository
public interface SponsoredProductRepository extends JpaRepository<SponsoredProduct, String> {
    
    List<SponsoredProduct> findByCampaignId(String campaignId);
    List<SponsoredProduct> findByProductId(String productId);
    List<SponsoredProduct> findByStatus(String status);
}
