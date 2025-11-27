package com.handmade.ecommerce.productorch.service;

import com.handmade.ecommerce.productorch.service.BaseProductOrchService;
import com.handmade.ecommerce.productorch.service.ProductContext;

public class CreateShippingProfileCommandService extends BaseProductOrchService {

    @Override
    protected void doProcess(ProductContext productContext) {
        System.out.println("Creating shipping profile...");
        super.doProcess(productContext);
    }
}
