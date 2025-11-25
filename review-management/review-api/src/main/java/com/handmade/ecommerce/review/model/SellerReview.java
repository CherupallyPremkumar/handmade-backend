package com.handmade.ecommerce.review.model;

import jakarta.persistence.*;

/**
 * SellerReview - Review for sellers (extends Review base class)
 */
@Entity
@DiscriminatorValue("SELLER")
public class SellerReview extends Review {
    
    @Column(name = "seller_id", length = 50, nullable = false)
    private String sellerId;
    
    // Constructors
    public SellerReview() {
        super();
    }
    
    public SellerReview(String reviewId, String sellerId, String customerId, Integer rating) {
        super();
        this.reviewId = reviewId;
        this.sellerId = sellerId;
        this.customerId = customerId;
        this.rating = rating;
    }
    
    // Getters and Setters for SellerReview-specific fields
    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
}

