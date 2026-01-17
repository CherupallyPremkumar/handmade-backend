package com.handmade.ecommerce.financialtransaction.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.finance.model.FinancialTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.financialtransaction.configuration.dao.FinancialTransactionRepository;
import java.util.Optional;

public class FinancialTransactionEntityStore implements EntityStore<FinancialTransaction>{
    @Autowired private FinancialTransactionRepository financialtransactionRepository;

	@Override
	public void store(FinancialTransaction entity) {
        financialtransactionRepository.save(entity);
	}

	@Override
	public FinancialTransaction retrieve(String id) {
        Optional<FinancialTransaction> entity = financialtransactionRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find FinancialTransaction with ID " + id);
	}

}
