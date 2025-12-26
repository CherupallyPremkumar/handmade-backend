package com.handmade.ecommerce.seller.configuration.dao;

import com.handmade.ecommerce.seller.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, String> {

    /**
     * Check if a seller exists with the given contact email.
     * Used for duplicate email validation during seller creation.
     */
    boolean existsByContactEmail(String contactEmail);

}
