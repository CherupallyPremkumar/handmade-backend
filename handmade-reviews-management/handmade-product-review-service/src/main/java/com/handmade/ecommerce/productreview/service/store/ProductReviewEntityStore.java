package com.handmade.ecommerce.productreview.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.reviews.model.ProductReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.productreview.configuration.dao.ProductReviewRepository;
import java.util.Optional;

public class ProductReviewEntityStore implements EntityStore<ProductReview>{
    @Autowired private ProductReviewRepository productreviewRepository;

	@Override
	public void store(ProductReview entity) {
        productreviewRepository.save(entity);
	}

	@Override
	public ProductReview retrieve(String id) {
        Optional<ProductReview> entity = productreviewRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find ProductReview with ID " + id);
	}

}
