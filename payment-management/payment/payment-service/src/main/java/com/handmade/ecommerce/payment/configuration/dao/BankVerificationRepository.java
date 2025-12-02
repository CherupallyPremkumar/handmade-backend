package com.handmade.ecommerce.payment.configuration.dao;

import com.handmade.ecommerce.payment.model.BankVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankVerificationRepository extends JpaRepository<BankVerification, String> {

    /**
     * Find verification by reference ID from payment gateway
     */
    Optional<BankVerification> findByReferenceId(String referenceId);

    /**
     * Find all verifications for a seller
     */
    List<BankVerification> findBySellerId(String sellerId);

    /**
     * Find verification by account number
     */
    Optional<BankVerification> findByAccountNumber(String accountNumber);

    /**
     * Find all verifications by status
     */
    List<BankVerification> findByStatus(String status);

    /**
     * Find verifications by seller and status
     */
    List<BankVerification> findBySellerIdAndStatus(String sellerId, String status);

    /**
     * Find verifications by provider name
     */
    List<BankVerification> findByProviderName(String providerName);

    /**
     * Check if account number has been verified
     */
    boolean existsByAccountNumberAndStatus(String accountNumber, String status);

    /**
     * Find latest verification for an account
     */
    Optional<BankVerification> findFirstByAccountNumberOrderByCreatedAtDesc(String accountNumber);
}
