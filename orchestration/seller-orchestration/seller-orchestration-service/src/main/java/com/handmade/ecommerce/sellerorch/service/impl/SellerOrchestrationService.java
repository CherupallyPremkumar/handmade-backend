package com.handmade.ecommerce.sellerorch.service.impl;

import com.handmade.ecommerce.sellerorch.SellerContext;
import com.handmade.ecommerce.sellerorch.SellerorchService;
import com.handmade.ecommerce.sellerorch.service.SellerEntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerOrchestrationService implements SellerorchService {

    private static final Logger logger =
            LoggerFactory.getLogger(SellerOrchestrationService.class);
    @Autowired
    private SellerEntryPoint sellerEntryPoint;

    @Override
    public void create(SellerContext sellerContext) {
        logger.info("Starting seller orchestration for create operation...");
        sellerEntryPoint.execute(sellerContext);
        logger.info("Seller orchestration completed.");
    }
}
