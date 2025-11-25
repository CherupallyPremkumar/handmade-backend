package com.handmade.ecommerce.content.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * Page - Entity for static content pages (About Us, FAQ, etc.)
 */
@Entity
@Table(name = "content_pages", indexes = {
    @Index(name = "idx_page_slug", columnList = "slug", unique = true),
    @Index(name = "idx_page_status", columnList = "status")
})
public class Page {
    
    @Id
    @Column(name = "page_id", length = 50)
    private String pageId;
    
    @Column(name = "slug", length = 100, nullable = false, unique = true)
    private String slug;
    
    @Column(name = "title", length = 255, nullable = false)
    private String title;
    
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private PageStatus status;
    
    @Column(name = "meta_title", length = 255)
    private String metaTitle;
    
    @Column(name = "meta_description", length = 500)
    private String metaDescription;
    
    @Column(name = "published_at")
    private Instant publishedAt;
    
    @Column(name = "created_at")
    private Instant createdAt;
    
    @Column(name = "updated_at")
    private Instant updatedAt;
    
    // Constructors
    public Page() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.status = PageStatus.DRAFT;
    }
    
    // Business methods
    public void publish() {
        this.status = PageStatus.PUBLISHED;
        this.publishedAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
    public void unpublish() {
        this.status = PageStatus.DRAFT;
        this.updatedAt = Instant.now();
    }
    
    // Getters and Setters
    public String getPageId() { return pageId; }
    public void setPageId(String pageId) { this.pageId = pageId; }
    
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public PageStatus getStatus() { return status; }
    public void setStatus(PageStatus status) { this.status = status; }
    
    public String getMetaTitle() { return metaTitle; }
    public void setMetaTitle(String metaTitle) { this.metaTitle = metaTitle; }
    
    public String getMetaDescription() { return metaDescription; }
    public void setMetaDescription(String metaDescription) { this.metaDescription = metaDescription; }
    
    public Instant getPublishedAt() { return publishedAt; }
    public void setPublishedAt(Instant publishedAt) { this.publishedAt = publishedAt; }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    
    public enum PageStatus {
        DRAFT,
        PUBLISHED,
        ARCHIVED
    }
}
