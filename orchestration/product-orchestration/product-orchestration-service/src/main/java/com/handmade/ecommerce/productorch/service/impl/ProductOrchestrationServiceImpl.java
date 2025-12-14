package com.handmade.ecommerce.productorch.service.impl;

import com.handmade.ecommerce.productorch.service.ProductContext;
import com.handmade.ecommerce.productorch.service.ProductEntryPoint;
import com.handmade.ecommerce.productorch.service.ProductorchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductOrchestrationServiceImpl implements ProductorchService {

    private static final Logger logger =
            LoggerFactory.getLogger(ProductOrchestrationServiceImpl.class);
    @Autowired
    private ProductEntryPoint productEntryPoint;

    @Override
    public void create(ProductContext productContext) {
        logger.info("Starting product orchestration for create operation...");
        productEntryPoint.execute(productContext);
        logger.info("Product orchestration completed.");
    }

}