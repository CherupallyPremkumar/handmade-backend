package com.handmade.ecommerce.seller.account.infrastructure.persistence;

import com.handmade.ecommerce.seller.account.domain.aggregate.SellerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerAccount (Onboarding)
 */
@Repository
public interface SellerAccountRepository extends JpaRepository<SellerAccount, String> {
    boolean existsByEmail(String email);

    SellerAccount findByEmail(String email);
}
