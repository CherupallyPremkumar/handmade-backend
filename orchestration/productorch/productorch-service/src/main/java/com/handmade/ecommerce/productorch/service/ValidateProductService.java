package com.handmade.ecommerce.productorch.service;

import org.chenile.owiz.Command;
import org.springframework.stereotype.Component;

public class ValidateProductService extends BaseProductOrchService{


    @Override
    protected void doProcess(ProductContext productContext) {
        productContext.addCommand("validateProductService");
        super.doProcess(productContext);
    }

    @Override
    protected void doPostProcessing(ProductContext productContext) {
        System.out.println("Validating product in ValidateProductService");
        super.doPostProcessing(productContext);
    }

    @Override
    protected void doPreProcessing(ProductContext productContext) {
        System.out.println("Validating product in ValidateProductService");
        super.doPreProcessing(productContext);
    }
}


