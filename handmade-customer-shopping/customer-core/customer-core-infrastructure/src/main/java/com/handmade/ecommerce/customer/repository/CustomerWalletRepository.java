package com.handmade.ecommerce.customer;

import com.handmade.ecommerce.customer.entity.CustomerWallet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for CustomerWallet
 * Generated from entity file
 */
@Repository
public interface CustomerWalletRepository extends JpaRepository<CustomerWallet, String> {
    
    List<CustomerWallet> findByCustomerId(String customerId);
}
