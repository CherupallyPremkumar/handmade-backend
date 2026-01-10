package com.handmade.ecommerce.seller.messaging;

import com.handmade.ecommerce.seller.messaging.entity.SellerConversation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerConversation
 * Generated from entity file
 */
@Repository
public interface SellerConversationRepository extends JpaRepository<SellerConversation, String> {
    
    List<SellerConversation> findByCustomerId(String customerId);
    List<SellerConversation> findBySellerId(String sellerId);
    List<SellerConversation> findByProductId(String productId);
    List<SellerConversation> findByOrderId(String orderId);
    List<SellerConversation> findByStatus(String status);
}
