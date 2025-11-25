package com.handmade.ecommerce.content.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * Media - Entity for managing images, videos, and documents
 */
@Entity
@Table(name = "content_media")
public class Media {
    
    @Id
    @Column(name = "media_id", length = 50)
    private String mediaId;
    
    @Column(name = "file_name", length = 255, nullable = false)
    private String fileName;
    
    @Column(name = "file_url", length = 500, nullable = false)
    private String fileUrl;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
    private MediaType type;
    
    @Column(name = "file_size")
    private Long fileSize; // in bytes
    
    @Column(name = "mime_type", length = 100)
    private String mimeType;
    
    @Column(name = "alt_text", length = 255)
    private String altText;
    
    @Column(name = "uploaded_by", length = 50)
    private String uploadedBy;
    
    @Column(name = "uploaded_at")
    private Instant uploadedAt;
    
    // Constructors
    public Media() {
        this.uploadedAt = Instant.now();
    }
    
    // Getters and Setters
    public String getMediaId() { return mediaId; }
    public void setMediaId(String mediaId) { this.mediaId = mediaId; }
    
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    
    public MediaType getType() { return type; }
    public void setType(MediaType type) { this.type = type; }
    
    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
    
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    
    public String getAltText() { return altText; }
    public void setAltText(String altText) { this.altText = altText; }
    
    public String getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(String uploadedBy) { this.uploadedBy = uploadedBy; }
    
    public Instant getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(Instant uploadedAt) { this.uploadedAt = uploadedAt; }
    
    public enum MediaType {
        IMAGE,
        VIDEO,
        DOCUMENT,
        AUDIO,
        ARCHIVE
    }
}
