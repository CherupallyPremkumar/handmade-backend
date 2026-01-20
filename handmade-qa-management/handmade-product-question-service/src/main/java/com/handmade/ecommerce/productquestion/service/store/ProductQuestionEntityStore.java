package com.handmade.ecommerce.productquestion.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.qa.model.ProductQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.productquestion.configuration.dao.ProductQuestionRepository;
import java.util.Optional;

public class ProductQuestionEntityStore implements EntityStore<ProductQuestion>{
    @Autowired private ProductQuestionRepository productquestionRepository;

	@Override
	public void store(ProductQuestion entity) {
        productquestionRepository.save(entity);
	}

	@Override
	public ProductQuestion retrieve(String id) {
        Optional<ProductQuestion> entity = productquestionRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find ProductQuestion with ID " + id);
	}

}
