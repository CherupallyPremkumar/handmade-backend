package com.handmade.ecommerce.product.service;

import com.handmade.ecommerce.product.dto.CreateProductRequest;
import com.handmade.ecommerce.product.dto.ProductResponse;
import com.handmade.ecommerce.product.service.ProductService;
import com.handmade.ecommerce.product.domain.model.Product;
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
    public ProductResponse createProduct(CreateProductRequest createProductRequest) {
        System.out.println("entered product service impl");
        validateRequest(createProductRequest);
        Product product = makeObject(createProductRequest);
        super.create(product);
        return toProductResponse(product);
    }

    /**
     * Map CreateProductRequest to Product entity
     */
    private Product makeObject(CreateProductRequest command) {
        Product product = new Product();

        // Basic fields
        product.setName(command.getName());
        product.setDescription(command.getDescription());
        product.setSalesDescription(command.getSalesDescription());
        product.setDetailsDescription(command.getDetailsDescription());

        // Category - ensure categoryIds is used
        if (command.getCategoryIds() != null && !command.getCategoryIds().isEmpty()) {
            product.setCategoryId(command.getCategoryIds().get(0));
            // If there's a second category, treat it as subcategory
            if (command.getCategoryIds().size() > 1) {
                product.setSubCategoryId(command.getCategoryIds().get(1));
            }
        }

        // Tags
        if (command.getTags() != null) {
            product.setTags(command.getTags());
        }

        // TODO: Handle Product Attributes mapping correctly.
        // Currently Product entity has singular ProductAttribute which seems to
        // represent Product Type.
        // Logic masked for Phase 1 compilation fix.

        return product;
    }

    public void validateRequest(CreateProductRequest requestProduct) {
        if (requestProduct.getName() == null || requestProduct.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (requestProduct.getDescription() == null || requestProduct.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Product description is required");
        }
        if (requestProduct.getCategoryIds() == null || requestProduct.getCategoryIds().isEmpty()) {
            throw new IllegalArgumentException("At least one category is required");
        }
        // TODO: Validation for product attributes disabled for now
        /*
         * if (requestProduct.getProductAttributes() == null ||
         * requestProduct.getProductAttributes().isEmpty()) {
         * throw new
         * IllegalArgumentException("Product attributes are required to identify product type"
         * );
         * }
         */
    }

    @Override
    public java.util.List<ProductResponse> getProductsBySeller(String sellerId) {
        // Wrapper for findBySeller or similar
        // TODO: Implement actual query
        return java.util.Collections.emptyList();
    }

    @Override
    public ProductResponse updateProduct(String productId, CreateProductRequest request) {
        // TODO: Implement update logic
        // This should likely fetch the product, update fields, and potentially use STM
        // if state changes are involved
        // For now, returning null or throw
        throw new UnsupportedOperationException("Update not yet implemented");
    }

    @Override
    public ProductResponse getProduct(String productId) {
        org.chenile.workflow.dto.StateEntityServiceResponse<Product> response = super.retrieve(productId);
        Product product = response.getMutatedEntity();
        return toProductResponse(product);
    }

    private ProductResponse toProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        // Map other fields as needed
        return response;
    }
}
