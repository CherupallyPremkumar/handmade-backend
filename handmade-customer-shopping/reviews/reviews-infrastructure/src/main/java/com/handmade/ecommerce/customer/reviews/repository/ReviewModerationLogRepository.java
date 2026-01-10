package com.handmade.ecommerce.customer.reviews;

import com.handmade.ecommerce.customer.reviews.entity.ReviewModerationLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ReviewModerationLog
 * Generated from entity file
 */
@Repository
public interface ReviewModerationLogRepository extends JpaRepository<ReviewModerationLog, String> {
    
    List<ReviewModerationLog> findByReviewId(String reviewId);
    List<ReviewModerationLog> findByModeratorId(String moderatorId);
}
