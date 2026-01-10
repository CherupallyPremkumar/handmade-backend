package com.handmade.ecommerce.order;

import com.handmade.ecommerce.order.entity.OrderLine;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for OrderLine
 * Generated from entity file
 */
@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, String> {
    
    List<OrderLine> findByOrderId(String orderId);
    List<OrderLine> findByProductId(String productId);
    List<OrderLine> findByOfferId(String offerId);
}
