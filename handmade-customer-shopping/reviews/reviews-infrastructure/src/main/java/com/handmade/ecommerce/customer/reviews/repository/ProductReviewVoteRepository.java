package com.handmade.ecommerce.customer.reviews;

import com.handmade.ecommerce.customer.reviews.entity.ProductReviewVote;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ProductReviewVote
 * Generated from entity file
 */
@Repository
public interface ProductReviewVoteRepository extends JpaRepository<ProductReviewVote, String> {
    
    List<ProductReviewVote> findByReviewId(String reviewId);
    List<ProductReviewVote> findByCustomerId(String customerId);
    List<ProductReviewVote> findByVoteType(String voteType);
}
