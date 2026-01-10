package com.handmade.ecommerce.customer.reviews;

import com.handmade.ecommerce.customer.reviews.entity.ProductReview;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ProductReview
 * Generated from entity file
 */
@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, String> {
    
    List<ProductReview> findByProductId(String productId);
    List<ProductReview> findByCustomerId(String customerId);
    List<ProductReview> findByStatus(String status);
}
