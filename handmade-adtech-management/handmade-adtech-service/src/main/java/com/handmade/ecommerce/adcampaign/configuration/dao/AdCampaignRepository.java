package com.handmade.ecommerce.adcampaign.configuration.dao;

import com.handmade.ecommerce.adcampaign.model.AdCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface AdCampaignRepository extends JpaRepository<AdCampaign,String> {}
