package com.homebase.ecom.mybatis.order;

public class OrderItems {
    private String orderLineId;
    private String tenantId;
    private String productVariantId;
    private String productId;
    private String displayName;
    private String quantity;
    private String isDelivered;
    private String openedQuantity;
    private String pickedQuantity;
    private String packedQuantity;
    private String committedQuantity;
    private String price;


    public String getOrderLineId() {
        return orderLineId;
    }

    public OrderItems setOrderLineId(String orderLineId) {
        this.orderLineId = orderLineId;
        return this;
    }

    public String getTenantId() {
        return tenantId;
    }

    public OrderItems setTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public String getProductVariantId() {
        return productVariantId;
    }

    public OrderItems setProductVariantId(String productVariantId) {
        this.productVariantId = productVariantId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public OrderItems setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public OrderItems setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getQuantity() {
        return quantity;
    }

    public OrderItems setQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getIsDelivered() {
        return isDelivered;
    }

    public OrderItems setIsDelivered(String isDelivered) {
        this.isDelivered = isDelivered;
        return this;
    }


    public String getPrice() {
        return price;
    }

    public OrderItems setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getOpenedQuantity() {
        return openedQuantity;
    }

    public OrderItems setOpenedQuantity(String openedQuantity) {
        this.openedQuantity = openedQuantity;
        return this;
    }

    public String getPickedQuantity() {
        return pickedQuantity;
    }

    public OrderItems setPickedQuantity(String pickedQuantity) {
        this.pickedQuantity = pickedQuantity;
        return this;
    }

    public String getPackedQuantity() {
        return packedQuantity;
    }

    public OrderItems setPackedQuantity(String packedQuantity) {
        this.packedQuantity = packedQuantity;
        return this;
    }

    public String getCommittedQuantity() {
        return committedQuantity;
    }

    public OrderItems setCommittedQuantity(String committedQuantity) {
        this.committedQuantity = committedQuantity;
        return this;
    }

}
