package com.handmade.ecommerce.seller.messaging;

import com.handmade.ecommerce.seller.messaging.entity.SellerMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerMessage
 * Generated from entity file
 */
@Repository
public interface SellerMessageRepository extends JpaRepository<SellerMessage, String> {
    
    List<SellerMessage> findByConversationId(String conversationId);
    List<SellerMessage> findBySenderId(String senderId);
    List<SellerMessage> findBySenderType(String senderType);
}
