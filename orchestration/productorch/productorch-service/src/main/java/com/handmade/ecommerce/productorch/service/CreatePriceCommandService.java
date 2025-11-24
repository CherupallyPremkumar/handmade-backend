package com.handmade.ecommerce.productorch.service;

import org.chenile.owiz.Command;

public class CreatePriceCommandService implements Command<ProductContext> {
    @Override
    public void execute(ProductContext productContext) throws Exception {
        System.out.println("Creating price in CreatePriceCommandService");
    }
}
