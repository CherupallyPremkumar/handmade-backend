package com.handmade.ecommerce.customer.qa;

import com.handmade.ecommerce.customer.qa.entity.ProductQuestion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ProductQuestion
 * Generated from entity file
 */
@Repository
public interface ProductQuestionRepository extends JpaRepository<ProductQuestion, String> {
    
    List<ProductQuestion> findByProductId(String productId);
    List<ProductQuestion> findByCustomerId(String customerId);
    List<ProductQuestion> findByCustomerName(String customerName);
    List<ProductQuestion> findByStatus(String status);
    List<ProductQuestion> findByModerationStatus(String moderationStatus);
}
