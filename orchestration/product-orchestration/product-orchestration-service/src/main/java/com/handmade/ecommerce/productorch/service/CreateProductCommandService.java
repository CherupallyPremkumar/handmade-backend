package com.handmade.ecommerce.productorch.service;


import com.handmade.ecommerce.product.ProductService;
import com.handmade.ecommerce.product.model.Product;
import org.chenile.base.exception.ErrorNumException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CreateProductCommandService extends BaseProductOrchService {
    @Autowired
    @Qualifier("_productStateEntityService_")
    ProductService productService;

    @Override
    protected void doProcess(ProductContext productContext) {
        Product product;
        try {
            product= productService.createProduct(productContext.getRequestProduct());
        }catch (Exception e){
            throw new ErrorNumException();
        }
        productContext.put("product", product);
    }

    @Override
    protected void doPostProcessing(ProductContext productContext) {
        System.out.println("Creating product in CreateProductCommandService");
        super.doPostProcessing(productContext);
    }

    @Override
    protected void doPreProcessing(ProductContext productContext) {
        try {
            productService.validate(productContext.getRequestProduct());
        } catch (IllegalArgumentException e) {
            throw new org.chenile.base.exception.BadRequestException(e.getMessage());
        }
    }
}
