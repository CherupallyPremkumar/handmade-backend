package com.handmade.ecommerce.productorch.service;

import jakarta.persistence.Column;
import org.chenile.core.context.ChenileExchange;
import org.chenile.owiz.OrchExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class ProductEntryPoint {
    @Autowired
    private OrchExecutor<ProductContext> productOrchExecutor;

    public void execute(ProductContext pr) {
        try {
            productOrchExecutor.execute(pr);
        }catch(Exception e) {
            pr.setException(e);
        }
    }
}
