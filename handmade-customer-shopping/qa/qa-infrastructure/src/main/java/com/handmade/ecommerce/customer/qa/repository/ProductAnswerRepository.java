package com.handmade.ecommerce.customer.qa;

import com.handmade.ecommerce.customer.qa.entity.ProductAnswer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ProductAnswer
 * Generated from entity file
 */
@Repository
public interface ProductAnswerRepository extends JpaRepository<ProductAnswer, String> {
    
    List<ProductAnswer> findByQuestionId(String questionId);
    List<ProductAnswer> findByCustomerId(String customerId);
    List<ProductAnswer> findByCustomerName(String customerName);
    List<ProductAnswer> findBySellerId(String sellerId);
    List<ProductAnswer> findByStatus(String status);
    List<ProductAnswer> findByModerationStatus(String moderationStatus);
}
