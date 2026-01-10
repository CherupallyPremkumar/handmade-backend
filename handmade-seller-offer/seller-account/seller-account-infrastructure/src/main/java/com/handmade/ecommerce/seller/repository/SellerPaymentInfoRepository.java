package com.handmade.ecommerce.seller;

import com.handmade.ecommerce.seller.entity.SellerPaymentInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerPaymentInfo
 * Generated from entity file
 */
@Repository
public interface SellerPaymentInfoRepository extends JpaRepository<SellerPaymentInfo, String> {
    
    List<SellerPaymentInfo> findBySellerId(String sellerId);
    List<SellerPaymentInfo> findByIsDefaultActive(Boolean isDefaultActive);
}
