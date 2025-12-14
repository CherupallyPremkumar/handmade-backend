package com.handmade.ecommerce.command;

import java.util.List;
import java.util.Map;

public class CreateProductCommand {

    private String id;
    private String name;
    private String sku;
    private String description;
    private List<String> categoryId;
    private String salesDescription;
    private String detailsDescription;
    private List<String> tags;
    private ProductImage primaryImage;
    private Map<String, ProductAttributes> productAttributes;
    private List<CreateVariantCommand> variants;


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    

    public String getSalesDescription() {
        return salesDescription;
    }

    public void setSalesDescription(String salesDescription) {
        this.salesDescription = salesDescription;
    }

    public String getDetailsDescription() {
        return detailsDescription;
    }

    public void setDetailsDescription(String detailsDescription) {
        this.detailsDescription = detailsDescription;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(List<String> categoryId) {
        this.categoryId = categoryId;
    }

    public ProductImage getPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(ProductImage primaryImage) {
        this.primaryImage = primaryImage;
    }



    public List<CreateVariantCommand> getVariants() {
        return variants;
    }

    public void setVariants(List<CreateVariantCommand> variants) {
        this.variants = variants;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Map<String, ProductAttributes> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(Map<String, ProductAttributes> productAttributes) {
        this.productAttributes = productAttributes;
    }
}