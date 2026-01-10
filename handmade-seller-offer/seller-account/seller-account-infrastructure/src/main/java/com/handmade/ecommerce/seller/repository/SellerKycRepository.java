package com.handmade.ecommerce.seller;

import com.handmade.ecommerce.seller.entity.SellerKyc;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerKyc
 * Generated from entity file
 */
@Repository
public interface SellerKycRepository extends JpaRepository<SellerKyc, String> {
    
    List<SellerKyc> findBySellerId(String sellerId);
    List<SellerKyc> findByDocumentType(String documentType);
    List<SellerKyc> findByDocumentNumber(String documentNumber);
    List<SellerKyc> findByS3Key(String s3Key);
    List<SellerKyc> findByVerificationStatus(String verificationStatus);
}
