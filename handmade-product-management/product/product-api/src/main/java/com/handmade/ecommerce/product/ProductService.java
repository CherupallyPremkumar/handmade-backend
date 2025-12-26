package com.handmade.ecommerce.product;

import com.handmade.ecommerce.command.CreateProductCommand;
import com.handmade.ecommerce.product.model.Product;

public interface ProductService {
    Product createProduct(CreateProductCommand createProductCommand);

    void validate(CreateProductCommand requestProduct);
}
