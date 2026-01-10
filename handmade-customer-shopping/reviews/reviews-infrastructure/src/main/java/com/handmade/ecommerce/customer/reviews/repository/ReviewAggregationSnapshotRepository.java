package com.handmade.ecommerce.customer.reviews;

import com.handmade.ecommerce.customer.reviews.entity.ReviewAggregationSnapshot;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ReviewAggregationSnapshot
 * Generated from entity file
 */
@Repository
public interface ReviewAggregationSnapshotRepository extends JpaRepository<ReviewAggregationSnapshot, String> {
    
    Optional<ReviewAggregationSnapshot> findByProductId(String productId);

    // Existence checks
    boolean existsByProductId(String productId);
}
