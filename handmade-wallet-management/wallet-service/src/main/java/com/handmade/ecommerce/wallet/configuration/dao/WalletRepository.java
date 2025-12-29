// wallet-service/configuration/dao/WalletRepository.java
package com.handmade.ecommerce.wallet.configuration.dao;

import com.handmade.ecommerce.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {

    Optional<Wallet> findByUserIdAndUserType(String userId, String userType);

    Optional<Wallet> findByUserId(String userId);
}