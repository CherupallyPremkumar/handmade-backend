package com.handmade.ecommerce.orchestration.product.command;

import com.handmade.ecommerce.orchestration.product.context.ProductOrchContext;
import org.chenile.owiz.impl.Chain;
import org.springframework.stereotype.Component;

/**
 * Chain for product creation orchestration.
 */
@Component("product-creation-chain")
public class ProductCreationChain extends Chain<ProductOrchContext> {
    public ProductCreationChain() {
        super();
    }
}
