package com.handmade.ecommerce.support.casemanagement;

import com.handmade.ecommerce.support.casemanagement.entity.OrderNote;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for OrderNote
 * Generated from entity file
 */
@Repository
public interface OrderNoteRepository extends JpaRepository<OrderNote, String> {
    
    List<OrderNote> findByOrderId(String orderId);
    List<OrderNote> findByAgentId(String agentId);
}
