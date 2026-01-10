package com.handmade.ecommerce.order.finance;

import com.handmade.ecommerce.order.finance.entity.Payout;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Payout
 * Generated from entity file
 */
@Repository
public interface PayoutRepository extends JpaRepository<Payout, String> {
    
    List<Payout> findBySellerId(String sellerId);
    List<Payout> findByStatus(String status);
    List<Payout> findByBankReferenceId(String bankReferenceId);
}
