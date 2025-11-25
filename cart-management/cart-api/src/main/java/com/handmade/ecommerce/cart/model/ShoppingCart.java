package com.handmade.ecommerce.cart.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * ShoppingCart - Aggregate Root for Cart Management
 */
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {
    
    @Id
    @Column(name = "cart_id", length = 50)
    private String cartId;
    
    @Column(name = "customer_id", length = 50)
    private String customerId;
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();
    
    @Column(name = "subtotal", precision = 19, scale = 2)
    private BigDecimal subtotal;
    
    @Column(name = "total_discount", precision = 19, scale = 2)
    private BigDecimal totalDiscount;
    
    @Column(name = "total_amount", precision = 19, scale = 2)
    private BigDecimal totalAmount;
    
    @Column(name = "applied_coupon_code", length = 50)
    private String appliedCouponCode;
    
    @Column(name = "expires_at")
    private Instant expiresAt;
    
    @Column(name = "created_at")
    private Instant createdAt;
    
    @Column(name = "updated_at")
    private Instant updatedAt;
    
    // Constructors
    public ShoppingCart() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.expiresAt = Instant.now().plusSeconds(7 * 24 * 60 * 60); // 7 days
        this.subtotal = BigDecimal.ZERO;
        this.totalDiscount = BigDecimal.ZERO;
        this.totalAmount = BigDecimal.ZERO;
    }
    
    public ShoppingCart(String cartId, String customerId) {
        this();
        this.cartId = cartId;
        this.customerId = customerId;
    }
    
    // Business methods
    public void addItem(CartItem item) {
        // Check if product already exists
        CartItem existing = findItemByProductId(item.getProductId());
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + item.getQuantity());
            existing.calculateSubtotal();
        } else {
            cartItems.add(item);
            item.setCart(this);
        }
        recalculateTotals();
    }
    
    public void removeItem(CartItem item) {
        cartItems.remove(item);
        item.setCart(null);
        recalculateTotals();
    }
    
    public void updateItemQuantity(String productId, Integer newQuantity) {
        CartItem item = findItemByProductId(productId);
        if (item != null) {
            if (newQuantity <= 0) {
                removeItem(item);
            } else {
                item.setQuantity(newQuantity);
                item.calculateSubtotal();
                recalculateTotals();
            }
        }
    }
    
    public void applyCoupon(String couponCode, BigDecimal discountAmount) {
        this.appliedCouponCode = couponCode;
        this.totalDiscount = discountAmount;
        recalculateTotals();
    }
    
    public void removeCoupon() {
        this.appliedCouponCode = null;
        this.totalDiscount = BigDecimal.ZERO;
        recalculateTotals();
    }
    
    public void clear() {
        cartItems.clear();
        recalculateTotals();
    }
    
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
    
    public int getTotalItems() {
        return cartItems.stream()
            .mapToInt(CartItem::getQuantity)
            .sum();
    }
    
    private CartItem findItemByProductId(String productId) {
        return cartItems.stream()
            .filter(item -> item.getProductId().equals(productId))
            .findFirst()
            .orElse(null);
    }
    
    private void recalculateTotals() {
        this.subtotal = cartItems.stream()
            .map(CartItem::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        this.totalAmount = subtotal.subtract(totalDiscount);
        this.updatedAt = Instant.now();
    }
    
    // Getters and Setters
    public String getCartId() { return cartId; }
    public void setCartId(String cartId) { this.cartId = cartId; }
    
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    
    public List<CartItem> getCartItems() { return cartItems; }
    public void setCartItems(List<CartItem> cartItems) { this.cartItems = cartItems; }
    
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    
    public BigDecimal getTotalDiscount() { return totalDiscount; }
    public void setTotalDiscount(BigDecimal totalDiscount) { this.totalDiscount = totalDiscount; }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    
    public String getAppliedCouponCode() { return appliedCouponCode; }
    public void setAppliedCouponCode(String appliedCouponCode) { this.appliedCouponCode = appliedCouponCode; }
    
    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
