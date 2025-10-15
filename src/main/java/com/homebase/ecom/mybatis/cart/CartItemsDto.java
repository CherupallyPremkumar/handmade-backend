package com.homebase.ecom.mybatis.cart;
public class CartItemsDto {
    private String cartItemId;
    private String tenantId;
    private String productVariantId;
    private String productId;
    private String quantity;
    private String price;
    private String totalAmount;
    private String locationId;
    private String quantityAvailable;
    private String displayName;
    private String thumbnail;
    private String description;


    public String getCartItemId() {
        return cartItemId;
    }

    public CartItemsDto setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
        return this;
    }

    public String getTenantId() {
        return tenantId;
    }

    public CartItemsDto setTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public String getProductVariantId() {
        return productVariantId;
    }

    public CartItemsDto setProductVariantId(String productVariantId) {
        this.productVariantId = productVariantId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public CartItemsDto setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getQuantity() {
        return quantity;
    }

    public CartItemsDto setQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }


    public String tenantId() {
        return tenantId;
    }

    public String productVariantId() {
        return productVariantId;
    }

    public String productId() {
        return productId;
    }

    public String quantity() {
        return quantity;
    }

    public String price() {
        return price;
    }

    public CartItemsDto setPrice(String price) {
        this.price = price;
        return this;
    }

    public String totalAmount() {
        return totalAmount;
    }

    public CartItemsDto setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public String locationId() {
        return locationId;
    }

    public CartItemsDto setLocationId(String locationId) {
        this.locationId = locationId;
        return this;
    }

    public String quantityAvailable() {
        return quantityAvailable;
    }

    public CartItemsDto setQuantityAvailable(String quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
        return this;
    }

    public String displayName() {
        return displayName;
    }

    public CartItemsDto setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String thumbnail() {
        return thumbnail;
    }

    public CartItemsDto setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public String cartItemId() {
        return cartItemId;
    }

    public String description() {
        return description;
    }

    public CartItemsDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
