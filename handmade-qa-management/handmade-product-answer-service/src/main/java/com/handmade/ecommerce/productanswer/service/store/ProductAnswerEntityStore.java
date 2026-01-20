package com.handmade.ecommerce.productanswer.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.qa.model.ProductAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.productanswer.configuration.dao.ProductAnswerRepository;
import java.util.Optional;

public class ProductAnswerEntityStore implements EntityStore<ProductAnswer>{
    @Autowired private ProductAnswerRepository productanswerRepository;

	@Override
	public void store(ProductAnswer entity) {
        productanswerRepository.save(entity);
	}

	@Override
	public ProductAnswer retrieve(String id) {
        Optional<ProductAnswer> entity = productanswerRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find ProductAnswer with ID " + id);
	}

}
