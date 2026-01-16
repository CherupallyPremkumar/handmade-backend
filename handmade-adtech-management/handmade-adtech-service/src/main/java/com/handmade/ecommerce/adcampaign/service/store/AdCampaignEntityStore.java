package com.handmade.ecommerce.adcampaign.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.adcampaign.model.AdCampaign;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.adcampaign.configuration.dao.AdCampaignRepository;
import java.util.Optional;

public class AdCampaignEntityStore implements EntityStore<AdCampaign>{
    @Autowired private AdCampaignRepository adtechRepository;

	@Override
	public void store(AdCampaign entity) {
        adtechRepository.save(entity);
	}

	@Override
	public AdCampaign retrieve(String id) {
        Optional<AdCampaign> entity = adtechRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find AdCampaign with ID " + id);
	}

}
