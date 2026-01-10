package com.handmade.ecommerce.order.tax;

import com.handmade.ecommerce.order.tax.entity.TaxRate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for TaxRate
 * Generated from entity file
 */
@Repository
public interface TaxRateRepository extends JpaRepository<TaxRate, String> {
    
    List<TaxRate> findByJurisdictionId(String jurisdictionId);
    List<TaxRate> findByTaxType(String taxType);
    List<TaxRate> findByProductCategoryId(String productCategoryId);
    List<TaxRate> findByIsActive(Boolean isActive);
}
