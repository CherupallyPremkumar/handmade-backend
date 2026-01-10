package com.handmade.ecommerce.customer;

import com.handmade.ecommerce.customer.entity.GiftCard;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for GiftCard
 * Generated from entity file
 */
@Repository
public interface GiftCardRepository extends JpaRepository<GiftCard, String> {
    
    List<GiftCard> findByCustomerId(String customerId);
    Optional<GiftCard> findByClaimCode(String claimCode);
    List<GiftCard> findByCurrencyCode(String currencyCode);
    List<GiftCard> findByStatus(String status);

    // Existence checks
    boolean existsByClaimCode(String claimCode);
}
