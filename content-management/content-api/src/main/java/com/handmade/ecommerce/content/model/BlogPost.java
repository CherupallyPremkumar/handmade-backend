package com.handmade.ecommerce.content.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * BlogPost - Entity for blog articles and stories
 */
@Entity
@Table(name = "content_blog_posts", indexes = {
    @Index(name = "idx_post_slug", columnList = "slug", unique = true),
    @Index(name = "idx_post_status", columnList = "status"),
    @Index(name = "idx_post_author", columnList = "author_id")
})
public class BlogPost {
    
    @Id
    @Column(name = "post_id", length = 50)
    private String postId;
    
    @Column(name = "title", length = 255, nullable = false)
    private String title;
    
    @Column(name = "slug", length = 100, nullable = false, unique = true)
    private String slug;
    
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "excerpt", length = 500)
    private String excerpt;
    
    @Column(name = "author_id", length = 50)
    private String authorId;
    
    @Column(name = "featured_image_url", length = 500)
    private String featuredImageUrl;
    
    @ElementCollection
    @CollectionTable(name = "content_post_tags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private PostStatus status;
    
    @Column(name = "published_at")
    private Instant publishedAt;
    
    @Column(name = "created_at")
    private Instant createdAt;
    
    @Column(name = "updated_at")
    private Instant updatedAt;
    
    // Constructors
    public BlogPost() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.status = PostStatus.DRAFT;
    }
    
    // Business methods
    public void publish() {
        this.status = PostStatus.PUBLISHED;
        this.publishedAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
    public void addTag(String tag) {
        this.tags.add(tag);
        this.updatedAt = Instant.now();
    }
    
    // Getters and Setters
    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getExcerpt() { return excerpt; }
    public void setExcerpt(String excerpt) { this.excerpt = excerpt; }
    
    public String getAuthorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }
    
    public String getFeaturedImageUrl() { return featuredImageUrl; }
    public void setFeaturedImageUrl(String featuredImageUrl) { this.featuredImageUrl = featuredImageUrl; }
    
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    
    public PostStatus getStatus() { return status; }
    public void setStatus(PostStatus status) { this.status = status; }
    
    public Instant getPublishedAt() { return publishedAt; }
    public void setPublishedAt(Instant publishedAt) { this.publishedAt = publishedAt; }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    
    public enum PostStatus {
        DRAFT,
        PUBLISHED,
        ARCHIVED
    }
}
