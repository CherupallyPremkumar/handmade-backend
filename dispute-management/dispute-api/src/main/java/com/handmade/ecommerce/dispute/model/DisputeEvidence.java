package com.handmade.ecommerce.dispute.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * DisputeEvidence - Entity for dispute evidence (photos, videos, documents)
 */
@Entity
@Table(name = "dispute_evidence")
public class DisputeEvidence {
    
    @Id
    @Column(name = "evidence_id", length = 50)
    private String evidenceId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispute_id", nullable = false)
    private Dispute dispute;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "uploaded_by", length = 20, nullable = false)
    private Dispute.SenderType uploadedBy;
    
    @Column(name = "uploader_id", length = 50)
    private String uploaderId; // Customer ID or Seller ID
    
    @Enumerated(EnumType.STRING)
    @Column(name = "evidence_type", length = 20)
    private EvidenceType evidenceType;
    
    @Column(name = "file_name", length = 255)
    private String fileName;
    
    @Column(name = "file_url", length = 500)
    private String fileUrl;
    
    @Column(name = "file_size")
    private Long fileSize; // in bytes
    
    @Column(name = "mime_type", length = 100)
    private String mimeType;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "uploaded_at")
    private Instant uploadedAt;
    
    // Constructors
    public DisputeEvidence() {
        this.uploadedAt = Instant.now();
    }
    
    // Getters and Setters
    public String getEvidenceId() { return evidenceId; }
    public void setEvidenceId(String evidenceId) { this.evidenceId = evidenceId; }
    
    public Dispute getDispute() { return dispute; }
    public void setDispute(Dispute dispute) { this.dispute = dispute; }
    
    public Dispute.SenderType getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(Dispute.SenderType uploadedBy) { this.uploadedBy = uploadedBy; }
    
    public String getUploaderId() { return uploaderId; }
    public void setUploaderId(String uploaderId) { this.uploaderId = uploaderId; }
    
    public EvidenceType getEvidenceType() { return evidenceType; }
    public void setEvidenceType(EvidenceType evidenceType) { this.evidenceType = evidenceType; }
    
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    
    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
    
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Instant getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(Instant uploadedAt) { this.uploadedAt = uploadedAt; }
    
    /**
     * Evidence Type Enum
     */
    public enum EvidenceType {
        PHOTO,
        VIDEO,
        DOCUMENT,
        SCREENSHOT,
        TRACKING_INFO,
        OTHER
    }
}
