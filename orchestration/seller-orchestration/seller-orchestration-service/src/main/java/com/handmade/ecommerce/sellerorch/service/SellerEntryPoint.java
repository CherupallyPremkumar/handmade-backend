package com.handmade.ecommerce.sellerorch.service;

import org.chenile.owiz.OrchExecutor;
import org.springframework.beans.factory.annotation.Autowired;

public class SellerEntryPoint {
    @Autowired
    private OrchExecutor<SellerContext> sellerOrchExecutor;

    public void execute(SellerContext sellerContext) {
        try {
            sellerOrchExecutor.execute(sellerContext);
        } catch (Exception e) {
            sellerContext.setException(e);
        }
    }
}
