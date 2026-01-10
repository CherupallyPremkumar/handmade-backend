package com.handmade.ecommerce.customer.qa;

import com.handmade.ecommerce.customer.qa.entity.QaVote;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for QaVote
 * Generated from entity file
 */
@Repository
public interface QaVoteRepository extends JpaRepository<QaVote, String> {
    
    List<QaVote> findByCustomerId(String customerId);
    List<QaVote> findByQuestionId(String questionId);
    List<QaVote> findByAnswerId(String answerId);
    List<QaVote> findByVoteType(String voteType);
}
