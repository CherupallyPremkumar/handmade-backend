package com.handmade.ecommerce.order.finance;

import com.handmade.ecommerce.order.finance.entity.TaxCalculation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for TaxCalculation
 * Generated from entity file
 */
@Repository
public interface TaxCalculationRepository extends JpaRepository<TaxCalculation, String> {
    
    List<TaxCalculation> findByOrderId(String orderId);
    List<TaxCalculation> findByOrderLineId(String orderLineId);
    List<TaxCalculation> findByTaxCode(String taxCode);
}
