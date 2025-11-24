package com.handmade.ecommerce.product.service;

import com.handmade.ecommerce.command.CreateProductCommand;
import com.handmade.ecommerce.product.ProductService;
import com.handmade.ecommerce.product.model.Product;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

public  class ProductServiceImpl extends StateEntityServiceImpl<Product> implements ProductService {

    public ProductServiceImpl(STM<Product> stm, STMActionsInfoProvider stmActionsInfoProvider, EntityStore<Product> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    public Product createProduct(CreateProductCommand createProductCommand) {
        System.out.println("entered product service impl");
        Product product=new Product();
        super.create(product);
        return product;
    }
}
