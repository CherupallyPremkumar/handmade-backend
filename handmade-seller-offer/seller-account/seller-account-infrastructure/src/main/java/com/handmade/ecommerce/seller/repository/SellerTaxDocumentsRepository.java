package com.handmade.ecommerce.seller;

import com.handmade.ecommerce.seller.entity.SellerTaxDocuments;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerTaxDocuments
 * Generated from entity file
 */
@Repository
public interface SellerTaxDocumentsRepository extends JpaRepository<SellerTaxDocuments, String> {
    
    List<SellerTaxDocuments> findByTaxInfoId(String taxInfoId);
}
