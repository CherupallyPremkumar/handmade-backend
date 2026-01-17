package com.handmade.ecommerce.productreview.configuration.dao;

import com.handmade.ecommerce.reviews.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface ProductReviewRepository extends JpaRepository<ProductReview,String> {}
