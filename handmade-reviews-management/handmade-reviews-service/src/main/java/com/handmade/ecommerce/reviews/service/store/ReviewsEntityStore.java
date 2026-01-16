package com.handmade.ecommerce.reviews.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.reviews.model.Reviews;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.reviews.configuration.dao.ReviewsRepository;
import java.util.Optional;

public class ReviewsEntityStore implements EntityStore<Reviews>{
    @Autowired private ReviewsRepository reviewsRepository;

	@Override
	public void store(Reviews entity) {
        reviewsRepository.save(entity);
	}

	@Override
	public Reviews retrieve(String id) {
        Optional<Reviews> entity = reviewsRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Reviews with ID " + id);
	}

}
