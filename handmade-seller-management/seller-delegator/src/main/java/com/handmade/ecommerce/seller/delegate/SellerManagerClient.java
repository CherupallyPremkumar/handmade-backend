package com.handmade.ecommerce.seller.delegate;

import com.handmade.ecommerce.seller.domain.aggregate.Seller;
import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.dto.command.*;

/**
 * Business Delegate for Seller Management
 * Allows remote calls to the Seller Manager service
 * Following the same delegation pattern as Platform Management
 * 
 * Handles both SellerAccount (lifecycle) and Seller (store) operations
 */
public interface SellerManagerClient {
    
    // ===== SellerAccount Lifecycle Operations =====
    
    /**
     * Register a new seller account
     * Initial state: PENDING_VERIFICATION
     */
    SellerAccount registerSellerAccount(CreateSellerRequest request);
    
    /**
     * Submit application for verification
     * DRAFT → PENDING_VERIFICATION
     */
    SellerAccount submitApplication(String accountId, SubmitApplicationPayload payload);
    
    /**
     * Approve seller account (admin action)
     * PENDING_VERIFICATION → ACTIVE
     */
    SellerAccount approveSellerAccount(String accountId, ApproveSellerPayload payload);
    
    /**
     * Reject seller application (admin action)
     */
    SellerAccount rejectSellerAccount(String accountId, RejectSellerPayload payload);
    
    /**
     * Suspend seller account
     * ACTIVE → SUSPENDED
     */
    SellerAccount suspendSellerAccount(String accountId, SuspendSellerPayload payload);
    
    /**
     * Reactivate suspended account
     * SUSPENDED → ACTIVE
     */
    SellerAccount reactivateSellerAccount(String accountId, ReactivateSellerPayload payload);
    
    /**
     * Delete seller account
     */
    SellerAccount deleteSellerAccount(String accountId, DeleteSellerPayload payload);
    
    // ===== Generic Event Processing =====
    
    /**
     * Process any state machine event for SellerAccount
     */
    SellerAccount processSellerAccountEvent(String accountId, String event, Object payload);
    
    /**
     * Process any state machine event for Seller store
     */
    Seller processSellerEvent(String sellerId, String event, Object payload);
    
    // ===== Query Operations =====
    
    /**
     * Retrieve seller account by ID
     */
    SellerAccount getSellerAccount(String accountId);
    
    /**
     * Retrieve seller store by ID
     */
    Seller getSeller(String sellerId);
}
