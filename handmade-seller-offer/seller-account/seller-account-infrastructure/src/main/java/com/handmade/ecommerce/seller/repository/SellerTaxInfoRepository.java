package com.handmade.ecommerce.seller;

import com.handmade.ecommerce.seller.entity.SellerTaxInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerTaxInfo
 * Generated from entity file
 */
@Repository
public interface SellerTaxInfoRepository extends JpaRepository<SellerTaxInfo, String> {
    
    List<SellerTaxInfo> findBySellerId(String sellerId);
    List<SellerTaxInfo> findByCompanyName(String companyName);
    List<SellerTaxInfo> findByCompanyType(String companyType);
    List<SellerTaxInfo> findByBusinessRegistrationNumber(String businessRegistrationNumber);
    List<SellerTaxInfo> findByPanNumber(String panNumber);
    List<SellerTaxInfo> findByGstNumber(String gstNumber);
    List<SellerTaxInfo> findByVatNumber(String vatNumber);
    List<SellerTaxInfo> findByTaxState(String taxState);
    List<SellerTaxInfo> findByPostalCode(String postalCode);
    List<SellerTaxInfo> findByIsActive(Boolean isActive);
}
