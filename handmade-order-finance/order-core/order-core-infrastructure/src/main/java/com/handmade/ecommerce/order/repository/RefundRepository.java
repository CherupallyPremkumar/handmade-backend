package com.handmade.ecommerce.order;

import com.handmade.ecommerce.order.entity.Refund;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Refund
 * Generated from entity file
 */
@Repository
public interface RefundRepository extends JpaRepository<Refund, String> {
    
    List<Refund> findByReturnRequestId(String returnRequestId);
    List<Refund> findByTransactionId(String transactionId);
}
