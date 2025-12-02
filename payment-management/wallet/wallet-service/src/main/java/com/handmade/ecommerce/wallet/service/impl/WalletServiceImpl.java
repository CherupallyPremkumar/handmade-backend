package com.handmade.ecommerce.wallet.service.impl;

import com.handmade.ecommerce.wallet.configuration.dao.WalletTransactionRepository;
import com.handmade.ecommerce.wallet.model.WalletTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.wallet.model.Wallet;
import com.handmade.ecommerce.wallet.service.WalletService;

import com.handmade.ecommerce.wallet.configuration.dao.WalletRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class WalletServiceImpl implements WalletService{
    private static final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletTransactionRepository transactionRepository;

    @Override
    public Wallet getOrCreateWallet(String userId, String userType, String currency) {
        return walletRepository.findByUserIdAndUserType(userId, userType)
                .orElseGet(() -> {
                    logger.info("Creating new wallet for user: {}", userId);
                    Wallet wallet = new Wallet();
                    wallet.setUserId(userId);
                    wallet.setUserType(userType);
                    wallet.setBalance(BigDecimal.ZERO);
                    wallet.setCurrency(currency);
                    wallet.setStatus("ACTIVE");
                    return walletRepository.save(wallet);
                });
    }

    @Override
    @Transactional
    public WalletTransaction credit(
            String userId,
            String paymentOrderId,
            BigDecimal amount,
            String description
    ) {
        logger.info("Crediting {} to user: {}", amount, userId);

        // Get wallet
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found for user: " + userId));

        // Record balance before
        BigDecimal balanceBefore = wallet.getBalance();

        // Update balance
        BigDecimal balanceAfter = balanceBefore.add(amount);
        wallet.setBalance(balanceAfter);
        walletRepository.save(wallet);

        // Create transaction record
        WalletTransaction transaction = new WalletTransaction();
        transaction.setWalletId(wallet.getId());
        transaction.setPaymentOrderId(paymentOrderId);
        transaction.setTransactionType("CREDIT");
        transaction.setAmount(amount);
        transaction.setBalanceBefore(balanceBefore);
        transaction.setBalanceAfter(balanceAfter);
        transaction.setDescription(description);

        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public WalletTransaction debit(
            String userId,
            String paymentOrderId,
            BigDecimal amount,
            String description
    ) {
        logger.info("Debiting {} from user: {}", amount, userId);

        // Get wallet
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found for user: " + userId));

        // Check sufficient balance
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }

        // Record balance before
        BigDecimal balanceBefore = wallet.getBalance();

        // Update balance
        BigDecimal balanceAfter = balanceBefore.subtract(amount);
        wallet.setBalance(balanceAfter);
        walletRepository.save(wallet);

        // Create transaction record
        WalletTransaction transaction = new WalletTransaction();
        transaction.setWalletId(wallet.getId());
        transaction.setPaymentOrderId(paymentOrderId);
        transaction.setTransactionType("DEBIT");
        transaction.setAmount(amount);
        transaction.setBalanceBefore(balanceBefore);
        transaction.setBalanceAfter(balanceAfter);
        transaction.setDescription(description);

        return transactionRepository.save(transaction);
    }

    @Override
    public BigDecimal getBalance(String userId) {
        return walletRepository.findByUserId(userId)
                .map(Wallet::getBalance)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public List<WalletTransaction> getTransactionHistory(String userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

        return transactionRepository.findByWalletIdOrderByCreatedAtDesc(wallet.getId());
    }
}