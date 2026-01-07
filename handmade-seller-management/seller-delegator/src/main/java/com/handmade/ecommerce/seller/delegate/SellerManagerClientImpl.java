package com.handmade.ecommerce.seller.delegate;

import com.handmade.ecommerce.seller.api.SellerService;
import com.handmade.ecommerce.seller.domain.aggregate.Seller;
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

    @Override
    public Seller processSellerEvent(String sellerId, String event, Object payload) {
        logger.debug("Processing event '{}' for seller store: {}", event, sellerId);
        return (Seller) sellerServiceProxy.processById(sellerId, event, payload).getMutatedEntity();
    }

    @Override
    public Seller getSeller(String sellerId) {
        logger.debug("Retrieving seller store: {}", sellerId);
        return (Seller) sellerServiceProxy.retrieve(sellerId).getMutatedEntity();
    }

    @Override
    public boolean existsByEmail(String email) {
        logger.debug("Checking email existence in operational context: {}", email);
        return sellerServiceProxy.existsByEmail(email);
    }
}
