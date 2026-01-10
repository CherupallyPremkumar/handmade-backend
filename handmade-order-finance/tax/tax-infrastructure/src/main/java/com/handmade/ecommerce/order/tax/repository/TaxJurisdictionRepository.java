package com.handmade.ecommerce.order.tax;

import com.handmade.ecommerce.order.tax.entity.TaxJurisdiction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for TaxJurisdiction
 * Generated from entity file
 */
@Repository
public interface TaxJurisdictionRepository extends JpaRepository<TaxJurisdiction, String> {
    
    Optional<TaxJurisdiction> findByJurisdictionCode(String jurisdictionCode);
    List<TaxJurisdiction> findByJurisdictionName(String jurisdictionName);
    List<TaxJurisdiction> findByJurisdictionType(String jurisdictionType);
    List<TaxJurisdiction> findByCountryCode(String countryCode);
    List<TaxJurisdiction> findByStateProvince(String stateProvince);
    List<TaxJurisdiction> findByPostalCode(String postalCode);
    List<TaxJurisdiction> findByParentJurisdictionId(String parentJurisdictionId);
    List<TaxJurisdiction> findByIsActive(Boolean isActive);

    // Existence checks
    boolean existsByJurisdictionCode(String jurisdictionCode);
}
