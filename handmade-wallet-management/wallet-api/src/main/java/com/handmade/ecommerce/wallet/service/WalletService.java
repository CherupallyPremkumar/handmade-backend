package com.handmade.ecommerce.wallet.service;

import com.handmade.ecommerce.wallet.model.CreditWalletRequest;
import com.handmade.ecommerce.wallet.model.DebitWalletRequest;
import com.handmade.ecommerce.wallet.model.Wallet;
import com.handmade.ecommerce.wallet.model.WalletTransaction;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {

        Wallet getOrCreateWallet(String userId, String userType, String currency);

        /**
         * Credit wallet using request object
         */
        WalletTransaction credit(CreditWalletRequest request);

        WalletTransaction debit(DebitWalletRequest request);

        BigDecimal getBalance(String userId);

        List<WalletTransaction> getTransactionHistory(String userId);
}
