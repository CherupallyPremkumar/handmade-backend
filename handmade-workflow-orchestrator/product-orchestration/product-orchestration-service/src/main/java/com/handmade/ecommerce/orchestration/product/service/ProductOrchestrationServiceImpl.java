package com.handmade.ecommerce.orchestration.product.service;

import com.handmade.ecommerce.orchestration.product.context.ProductOrchContext;
import com.handmade.ecommerce.orchestration.product.dto.ProductOrchestrationRequest;
import com.handmade.ecommerce.product.dto.ProductResponse;
import org.chenile.owiz.impl.Chain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("_productOrchestrationService_")
public class ProductOrchestrationServiceImpl implements ProductOrchestrationService {

    @Autowired
    @Qualifier("product-creation-chain")
    private Chain<ProductOrchContext> productCreationChain;

    @Override
    public ProductResponse orchestrateProduct(ProductOrchestrationRequest request) {
        ProductOrchContext context = new ProductOrchContext();
        context.setRequest(request);

        try {
            // OWIZ Chain execution
            productCreationChain.execute(context);
            return context.getProductResponse();
        } catch (Exception e) {
            throw new RuntimeException("Product orchestration failed", e);
        }
    }
}
