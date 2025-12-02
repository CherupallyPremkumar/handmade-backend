package com.handmade.ecommerce.wallet.service;

import com.handmade.ecommerce.wallet.model.Wallet;
import com.handmade.ecommerce.wallet.model.WalletTransaction;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {

    Wallet getOrCreateWallet(String userId, String userType, String currency);

    WalletTransaction credit(
            String userId,
            String paymentOrderId,
            BigDecimal amount,
            String description
    );


    WalletTransaction debit(
            String userId,
            String paymentOrderId,
            BigDecimal amount,
            String description
    );

    BigDecimal getBalance(String userId);

    List<WalletTransaction> getTransactionHistory(String userId);
}
