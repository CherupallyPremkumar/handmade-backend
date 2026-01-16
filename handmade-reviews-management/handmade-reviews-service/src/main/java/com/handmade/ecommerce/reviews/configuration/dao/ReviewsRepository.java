package com.handmade.ecommerce.reviews.configuration.dao;

import com.handmade.ecommerce.reviews.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface ReviewsRepository extends JpaRepository<Reviews,String> {}
