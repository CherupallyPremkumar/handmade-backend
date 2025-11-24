package com.handmade.ecommerce.command;


import java.util.List;

public class CreateVariantCommand {

    private String productId;
    private String id;
    private String sku;
    private String title;
    private List<CreatePriceCommand> prices;
    private CreateInventoryCommand inventory;
    private List<VariantImage> images;
    // Variant properties (color, size, etc…)
    private List<VariantAttribute> attributes;
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

    public List<VariantAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<VariantAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<CreatePriceCommand> getPrices() {
        return prices;
    }

    public void setPrices(List<CreatePriceCommand> prices) {
        this.prices = prices;
    }
}
