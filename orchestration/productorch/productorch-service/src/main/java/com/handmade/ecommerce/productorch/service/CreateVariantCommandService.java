package com.handmade.ecommerce.productorch.service;

import com.handmade.ecommerce.variant.VaraiantService;
import org.chenile.owiz.Command;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateVariantCommandService extends BaseProductOrchService  {
    @Autowired
    VaraiantService variantService;

    @Override
    protected void doProcess(ProductContext productContext) {
        productContext.addCommand("createVariantService");
        super.doProcess(productContext);
    }

    @Override
    protected void doPostProcessing(ProductContext productContext) {
        System.out.println("Creating variant in CreateVariantCommandService");
        super.doPostProcessing(productContext);
    }

    @Override
    protected void doPreProcessing(ProductContext productContext) {
        System.out.println("Creating variant in CreateVariantCommandService");
        super.doPreProcessing(productContext);
    }
}
