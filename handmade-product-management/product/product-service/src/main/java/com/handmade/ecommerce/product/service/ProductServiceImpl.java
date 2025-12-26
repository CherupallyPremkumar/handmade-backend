package com.handmade.ecommerce.product.service;

import com.handmade.ecommerce.command.CreateProductCommand;
import com.handmade.ecommerce.product.ProductService;
import com.handmade.ecommerce.product.model.Product;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

public class ProductServiceImpl extends StateEntityServiceImpl<Product> implements ProductService {

    public ProductServiceImpl(STM<Product> stm, STMActionsInfoProvider stmActionsInfoProvider,
            EntityStore<Product> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    public Product createProduct(CreateProductCommand createProductCommand) {
        System.out.println("entered product service impl");
        Product product = makeObject(createProductCommand);
        super.create(product);
        return product;
    }

    /**
     * Map CreateProductCommand to Product entity
     */
    private Product makeObject(CreateProductCommand command) {
        Product product = new Product();

        // Basic fields
        product.setName(command.getName());
        product.setDescription(command.getDescription());
        product.setSalesDescription(command.getSalesDescription());
        product.setDetailsDescription(command.getDetailsDescription());

        // Category - take first category from list
        if (command.getCategoryId() != null && !command.getCategoryId().isEmpty()) {
            product.setCategoryId(command.getCategoryId().get(0));
            // If there's a second category, treat it as subcategory
            if (command.getCategoryId().size() > 1) {
                product.setSubCategoryId(command.getCategoryId().get(1));
            }
        }

        // Tags
        if (command.getTags() != null) {
            product.setTags(command.getTags());
        }

        // Note: Images are managed separately via dedicated Image Upload API
        // Product can be created without images (DRAFT state)

        return product;
    }

    @Override
    public void validate(CreateProductCommand requestProduct) {
        if (requestProduct.getName() == null || requestProduct.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (requestProduct.getDescription() == null || requestProduct.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Product description is required");
        }
        if (requestProduct.getCategoryId() == null || requestProduct.getCategoryId().isEmpty()) {
            throw new IllegalArgumentException("At least one category is required");
        }
        if (requestProduct.getProductAttributes() == null || requestProduct.getProductAttributes().isEmpty()) {
            throw new IllegalArgumentException("Product attributes are required to identify product type");
        }
    }
}
