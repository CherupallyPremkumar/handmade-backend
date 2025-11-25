package com.handmade.ecommerce.cart.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * CartItem - Entity representing items in shopping cart
 */
@Entity
@Table(name = "cart_items")
public class CartItem {
    
    @Id
    @Column(name = "cart_item_id", length = 50)
    private String cartItemId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private ShoppingCart cart;
    
    @Column(name = "product_id", length = 50, nullable = false)
    private String productId;
    
    @Column(name = "product_name", length = 255)
    private String productName;
    
    @Column(name = "product_image_url", length = 500)
    private String productImageUrl;
    
    @Column(name = "seller_id", length = 50)
    private String sellerId;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "unit_price", precision = 19, scale = 2, nullable = false)
    private BigDecimal unitPrice;
    
    @Column(name = "subtotal", precision = 19, scale = 2)
    private BigDecimal subtotal;
    
    // Constructors
    public CartItem() {
        this.quantity = 1;
    }
    
    public CartItem(String cartItemId, String productId, Integer quantity, BigDecimal unitPrice) {
        this();
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        calculateSubtotal();
    }
    
    // Business methods
    public void calculateSubtotal() {
        if (unitPrice != null && quantity != null) {
            this.subtotal = unitPrice.multiply(new BigDecimal(quantity));
        }
    }
    
    public void incrementQuantity() {
        this.quantity++;
        calculateSubtotal();
    }
    
    public void decrementQuantity() {
        if (this.quantity > 1) {
            this.quantity--;
            calculateSubtotal();
        }
    }
    
    // Getters and Setters
    public String getCartItemId() { return cartItemId; }
    public void setCartItemId(String cartItemId) { this.cartItemId = cartItemId; }
    
    public ShoppingCart getCart() { return cart; }
    public void setCart(ShoppingCart cart) { this.cart = cart; }
    
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public String getProductImageUrl() { return productImageUrl; }
    public void setProductImageUrl(String productImageUrl) { this.productImageUrl = productImageUrl; }
    
    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { 
        this.quantity = quantity;
        calculateSubtotal();
    }
    
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { 
        this.unitPrice = unitPrice;
        calculateSubtotal();
    }
    
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
