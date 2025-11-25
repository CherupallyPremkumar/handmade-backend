package com.handmade.ecommerce.content.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * Banner - Entity for homepage and promotional banners
 */
@Entity
@Table(name = "content_banners")
public class Banner {
    
    @Id
    @Column(name = "banner_id", length = 50)
    private String bannerId;
    
    @Column(name = "title", length = 255)
    private String title;
    
    @Column(name = "image_url", length = 500, nullable = false)
    private String imageUrl;
    
    @Column(name = "link_url", length = 500)
    private String linkUrl;
    
    @Column(name = "display_order")
    private Integer displayOrder;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "position", length = 20)
    private BannerPosition position;
    
    @Column(name = "start_date")
    private Instant startDate;
    
    @Column(name = "end_date")
    private Instant endDate;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name = "created_at")
    private Instant createdAt;
    
    @Column(name = "updated_at")
    private Instant updatedAt;
    
    // Constructors
    public Banner() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.isActive = true;
        this.displayOrder = 0;
    }
    
    // Business methods
    public boolean isCurrentlyActive() {
        Instant now = Instant.now();
        return Boolean.TRUE.equals(isActive) &&
               (startDate == null || !now.isBefore(startDate)) &&
               (endDate == null || !now.isAfter(endDate));
    }
    
    // Getters and Setters
    public String getBannerId() { return bannerId; }
    public void setBannerId(String bannerId) { this.bannerId = bannerId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public String getLinkUrl() { return linkUrl; }
    public void setLinkUrl(String linkUrl) { this.linkUrl = linkUrl; }
    
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
    
    public BannerPosition getPosition() { return position; }
    public void setPosition(BannerPosition position) { this.position = position; }
    
    public Instant getStartDate() { return startDate; }
    public void setStartDate(Instant startDate) { this.startDate = startDate; }
    
    public Instant getEndDate() { return endDate; }
    public void setEndDate(Instant endDate) { this.endDate = endDate; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    
    public enum BannerPosition {
        HERO,
        SIDEBAR,
        FOOTER,
        CATEGORY_HEADER,
        PROMO_STRIP
    }
}
