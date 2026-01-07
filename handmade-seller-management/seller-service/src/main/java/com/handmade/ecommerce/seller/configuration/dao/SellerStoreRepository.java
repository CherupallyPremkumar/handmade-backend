package com.handmade.ecommerce.seller.configuration.dao;

import com.handmade.ecommerce.seller.domain.aggregate.SellerStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for SellerStore aggregate
 */
@Repository
public interface SellerStoreRepository extends JpaRepository<SellerStore, String> {

    /**
     * Find store by seller ID
     */
    Optional<SellerStore> findBySellerId(String sellerId);
}
