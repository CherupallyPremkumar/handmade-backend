package com.handmade.ecommerce.productorch.service;


import com.handmade.ecommerce.product.ProductService;
import com.handmade.ecommerce.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CreateProductCommandService extends BaseProductOrchService {
    @Autowired
    @Qualifier("_productStateEntityService_")
    ProductService productService;

    @Override
    protected void doProcess(ProductContext productContext) {
        productContext.addCommand("createProductService");
        super.doProcess(productContext);
    }

    @Override
    protected void doPostProcessing(ProductContext productContext) {
        System.out.println("Creating product in CreateProductCommandService");
        super.doPostProcessing(productContext);
    }

    @Override
    protected void doPreProcessing(ProductContext productContext) {
        System.out.println("Creating product in CreateProductCommandService");
        super.doPreProcessing(productContext);
    }
}
