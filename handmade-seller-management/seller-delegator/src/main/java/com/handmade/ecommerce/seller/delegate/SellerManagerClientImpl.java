package com.handmade.ecommerce.seller.delegate;

import com.handmade.ecommerce.seller.api.SellerAccountService;
import com.handmade.ecommerce.seller.api.SellerService;
import com.handmade.ecommerce.seller.domain.aggregate.Seller;
import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.dto.CreateSellerRequest;
import com.handmade.ecommerce.seller.dto.command.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Implementation of Seller Manager Client
 * Delegates calls to remote Seller Service via Chenile Proxy
 * 
 * Supports dual-aggregate pattern:
 * - SellerAccount operations (lifecycle, KYC)
 * - Seller operations (store management)
 */
public class SellerManagerClientImpl implements SellerManagerClient {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    @Qualifier("sellerServiceProxy")
    private SellerService sellerServiceProxy;

    @Autowired
    @Qualifier("sellerAccountServiceProxy")
    private SellerAccountService sellerAccountServiceProxy;

    @Override
    public SellerAccount registerSellerAccount(CreateSellerRequest request) {
        logger.info("Registering seller account via delegate: {}", request.getEmail());
        SellerAccount account = toSellerAccount(request);
        return (SellerAccount) sellerAccountServiceProxy.create(account).getMutatedEntity();
    }

    @Override
    public SellerAccount submitApplication(String accountId, SubmitApplicationPayload payload) {
        logger.info("Submitting application for seller: {}", accountId);
        return processSellerAccountEvent(accountId, "submitApplication", payload);
    }

    @Override
    public SellerAccount approveSellerAccount(String accountId, ApproveSellerPayload payload) {
        logger.info("Approving seller account via delegate: {}", accountId);
        return processSellerAccountEvent(accountId, "approve", payload);
    }

    @Override
    public SellerAccount rejectSellerAccount(String accountId, RejectSellerPayload payload) {
        logger.info("Rejecting seller account via delegate: {}", accountId);
        return processSellerAccountEvent(accountId, "reject", payload);
    }

    @Override
    public SellerAccount suspendSellerAccount(String accountId, SuspendSellerPayload payload) {
        logger.info("Suspending seller account via delegate: {}", accountId);
        return processSellerAccountEvent(accountId, "suspend", payload);
    }

    @Override
    public SellerAccount reactivateSellerAccount(String accountId, ReactivateSellerPayload payload) {
        logger.info("Reactivating seller account via delegate: {}", accountId);
        return processSellerAccountEvent(accountId, "reactivate", payload);
    }

    @Override
    public SellerAccount deleteSellerAccount(String accountId, DeleteSellerPayload payload) {
        logger.info("Deleting seller account via delegate: {}", accountId);
        return processSellerAccountEvent(accountId, "delete", payload);
    }

    @Override
    public SellerAccount processSellerAccountEvent(String accountId, String event, Object payload) {
        logger.debug("Processing event '{}' for seller account: {}", event, accountId);
        return (SellerAccount) (Object) sellerServiceProxy.processById(accountId, event, payload).getMutatedEntity();
    }

    @Override
    public Seller processSellerEvent(String sellerId, String event, Object payload) {
        logger.debug("Processing event '{}' for seller store: {}", event, sellerId);
        // TODO: Implement when SellerService is created
        throw new UnsupportedOperationException("Seller store operations not yet implemented");
    }

    @Override
    public SellerAccount getSellerAccount(String accountId) {
        logger.debug("Retrieving seller account: {}", accountId);
        return (SellerAccount) (Object) sellerServiceProxy.retrieve(accountId).getMutatedEntity();
    }

    @Override
    public Seller getSeller(String sellerId) {
        logger.debug("Retrieving seller store: {}", sellerId);
        // TODO: Implement when SellerService is created
        throw new UnsupportedOperationException("Seller store operations not yet implemented");
    }
    
    @Override
    public boolean existsByEmail(String email) {
        logger.debug("Checking email existence in operational context: {}", email);
        return sellerServiceProxy.existsByEmail(email);
    }

    // ===== Helper Methods =====
    
    private SellerAccount toSellerAccount(CreateSellerRequest request) {
        SellerAccount account = new SellerAccount();
        account.setEmail(request.getEmail());
        account.setBusinessName(request.getBusinessName());
        // TODO: Map countryCode and sellerType when SellerAccount supports them
        return account;
    }
}
