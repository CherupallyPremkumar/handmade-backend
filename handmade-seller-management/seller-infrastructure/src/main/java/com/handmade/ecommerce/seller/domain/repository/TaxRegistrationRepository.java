package com.handmade.ecommerce.seller.domain;

import com.handmade.ecommerce.seller.domain.entity.TaxRegistration;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for TaxRegistration
 * Generated from entity file
 */
@Repository
public interface TaxRegistrationRepository extends JpaRepository<TaxRegistration, String> {
    
    List<TaxRegistration> findByTaxIdNumber(String taxIdNumber);
    List<TaxRegistration> findByStatus(String status);
}
