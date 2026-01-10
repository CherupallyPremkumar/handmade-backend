package com.handmade.ecommerce.seller.domain;

import com.handmade.ecommerce.seller.domain.entity.TaxInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for TaxInfo
 * Generated from entity file
 */
@Repository
public interface TaxInfoRepository extends JpaRepository<TaxInfo, String> {
    
    List<TaxInfo> findBySellerId(String sellerId);
    List<TaxInfo> findByCompanyName(String companyName);
    List<TaxInfo> findByCompanyType(String companyType);
    List<TaxInfo> findByBusinessRegistrationNumber(String businessRegistrationNumber);
    List<TaxInfo> findByPanNumber(String panNumber);
    List<TaxInfo> findByGstNumber(String gstNumber);
    List<TaxInfo> findByVatNumber(String vatNumber);
    List<TaxInfo> findByTaxState(String taxState);
    List<TaxInfo> findByPostalCode(String postalCode);
}
