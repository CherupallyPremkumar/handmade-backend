package com.handmade.ecommerce.catalog.event.external;

public class ProductApprovedEvent {
    private String productId;

    public ProductApprovedEvent() {
    }

    public ProductApprovedEvent(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
