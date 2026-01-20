package com.handmade.ecommerce.financeprofile.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.finance.model.FinanceProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.financeprofile.configuration.dao.FinanceProfileRepository;
import java.util.Optional;

public class FinanceProfileEntityStore implements EntityStore<FinanceProfile>{
    @Autowired private FinanceProfileRepository financeprofileRepository;

	@Override
	public void store(FinanceProfile entity) {
        financeprofileRepository.save(entity);
	}

	@Override
	public FinanceProfile retrieve(String id) {
        Optional<FinanceProfile> entity = financeprofileRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find FinanceProfile with ID " + id);
	}

}
