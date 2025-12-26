package com.handmade.ecommerce.command;

import java.util.List;

public class CreateVariantCommand {

    private String productId;
    private String artisanId;
    private String id;
    private String sku;
    private String title;
    private String description;
    private String featureDescription;

    // Shipping profiles (supports multiple packaging options)
    private List<CreateShippingProfileCommand> shippingProfiles;

    private List<CreatePriceCommand> prices;
    private CreateInventoryCommand inventory;
    private List<VariantImage> images;
    // Variant properties (color, size, etc…)
    private List<VariantAttributeDTO> variantAttributes;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CreateInventoryCommand getInventory() {
        return inventory;
    }

    public void setInventory(CreateInventoryCommand inventory) {
        this.inventory = inventory;
    }

    public List<VariantImage> getImages() {
        return images;
    }

    public void setImages(List<VariantImage> images) {
        this.images = images;
    }

    public List<CreatePriceCommand> getPrices() {
        return prices;
    }

    public void setPrices(List<CreatePriceCommand> prices) {
        this.prices = prices;
    }

    public List<VariantAttributeDTO> getVariantAttributes() {
        return variantAttributes;
    }

    public void setVariantAttributes(List<VariantAttributeDTO> variantAttributes) {
        this.variantAttributes = variantAttributes;
    }

    public String getFeatureDescription() {
        return featureDescription;
    }

    public void setFeatureDescription(String featureDescription) {
        this.featureDescription = featureDescription;
    }

    public String getArtisanId() {
        return artisanId;
    }

    public void setArtisanId(String artisanId) {
        this.artisanId = artisanId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CreateShippingProfileCommand> getShippingProfiles() {
        return shippingProfiles;
    }

    public void setShippingProfiles(List<CreateShippingProfileCommand> shippingProfiles) {
        this.shippingProfiles = shippingProfiles;
    }
}
