package com.handmade.ecommerce.cart.model;

import com.handmade.ecommerce.core.model.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Shopping cart for e-commerce platform.
 * 
 * Supports:
 * - Guest browsing (sessionId only)
 * - Logged-in users (userId)
 * - Cart persistence
 * - Cart expiration
 * 
 * Flow:
 * 1. Guest adds items → cart created with sessionId
 * 2. Guest tries to checkout → must login
 * 3. After login → cart linked to userId
 * 4. Checkout → cart status = CHECKED_OUT
 * 5. Payment success → cart cleared
 */
@Getter
@Setter
@Entity
@Table(name = "hm_cart")
public class Cart extends AbstractJpaStateEntity {
    
    /**
     * User ID (null for guest carts)
     * Set when guest logs in or user creates cart
     */
    @Column(name = "user_id")
    private String userId;
    
    /**
     * Session ID for guest carts
     * Used to track cart before login
     */
    @Column(name = "session_id")
    private String sessionId;
    
    /**
     * Cart status
     * ACTIVE - Currently being used
     * CHECKED_OUT - Payment initiated
     * ABANDONED - Not used for X days
     * CONVERTED - Successfully converted to order
     */
    /**
     * Total amount (calculated from cart lines)
     */

    @Column(name = "tax_amount")
    private Money taxAmount;

    @Column(name = "total_amount", precision = 19, scale = 4)
    private Money totalAmount;
    
    /**
     * Currency code
     */
    @Column(name = "currency", length = 3)
    private String currency;
    
    /**
     * Cart expiration date
     * Auto-clear abandoned carts after this date
     */
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

}
