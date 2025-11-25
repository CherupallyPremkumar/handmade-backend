package com.handmade.ecommerce.review.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * Review - Abstract base class for all review types
 * Provides common fields and behavior for ProductReview and SellerReview
 */
@Entity
@Table(name = "reviews")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "review_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Review {
    
    @Id
    @Column(name = "review_id", length = 50)
    protected String reviewId;
    
    @Column(name = "customer_id", length = 50, nullable = false)
    protected String customerId;
    
    @Column(name = "order_id", length = 50)
    protected String orderId;
    
    @Column(name = "rating", nullable = false)
    protected Integer rating; // 1-5
    
    @Column(name = "comment", columnDefinition = "TEXT")
    protected String comment;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "review_status", length = 20)
    protected ReviewStatus reviewStatus;
    
    @Column(name = "helpful_count")
    protected Integer helpfulCount;
    
    @Column(name = "verified_purchase")
    protected Boolean verifiedPurchase;
    
    @Column(name = "created_at")
    protected Instant createdAt;
    
    @Column(name = "updated_at")
    protected Instant updatedAt;
    
    @Column(name = "approved_at")
    protected Instant approvedAt;
    
    // Constructors
    public Review() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.reviewStatus = ReviewStatus.PENDING;
        this.helpfulCount = 0;
        this.verifiedPurchase = false;
    }
    
    // Common business methods
    public void approve() {
        this.reviewStatus = ReviewStatus.APPROVED;
        this.approvedAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
    public void reject() {
        this.reviewStatus = ReviewStatus.REJECTED;
        this.updatedAt = Instant.now();
    }
    
    public void flag() {
        this.reviewStatus = ReviewStatus.FLAGGED;
        this.updatedAt = Instant.now();
    }
    
    public void incrementHelpfulCount() {
        this.helpfulCount++;
        this.updatedAt = Instant.now();
    }
    
    public boolean isApproved() {
        return ReviewStatus.APPROVED.equals(this.reviewStatus);
    }
    
    // Getters and Setters
    public String getReviewId() { return reviewId; }
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }
    
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.rating = rating;
    }
    
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    
    public ReviewStatus getReviewStatus() { return reviewStatus; }
    public void setReviewStatus(ReviewStatus reviewStatus) { this.reviewStatus = reviewStatus; }
    
    public Integer getHelpfulCount() { return helpfulCount; }
    public void setHelpfulCount(Integer helpfulCount) { this.helpfulCount = helpfulCount; }
    
    public Boolean getVerifiedPurchase() { return verifiedPurchase; }
    public void setVerifiedPurchase(Boolean verifiedPurchase) { this.verifiedPurchase = verifiedPurchase; }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    
    public Instant getApprovedAt() { return approvedAt; }
    public void setApprovedAt(Instant approvedAt) { this.approvedAt = approvedAt; }
    
    /**
     * Review Status Enum
     */
    public enum ReviewStatus {
        PENDING,
        APPROVED,
        REJECTED,
        FLAGGED
    }
}
