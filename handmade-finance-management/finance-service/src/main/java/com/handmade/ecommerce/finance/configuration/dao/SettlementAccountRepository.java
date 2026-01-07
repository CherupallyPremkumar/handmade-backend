package com.handmade.ecommerce.finance.configuration.dao;

import com.handmade.ecommerce.finance.domain.aggregate.SettlementAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettlementAccountRepository extends JpaRepository<SettlementAccount, String> {
    Optional<SettlementAccount> findBySellerId(String sellerId);
}
