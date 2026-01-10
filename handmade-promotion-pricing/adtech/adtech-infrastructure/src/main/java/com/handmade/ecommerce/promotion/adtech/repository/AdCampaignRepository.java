package com.handmade.ecommerce.promotion.adtech;

import com.handmade.ecommerce.promotion.adtech.entity.AdCampaign;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for AdCampaign
 * Generated from entity file
 */
@Repository
public interface AdCampaignRepository extends JpaRepository<AdCampaign, String> {
    
    List<AdCampaign> findByPlatformId(String platformId);
    List<AdCampaign> findBySellerId(String sellerId);
    List<AdCampaign> findByCampaignName(String campaignName);
    List<AdCampaign> findByBudgetType(String budgetType);
    List<AdCampaign> findByStatus(String status);
}
