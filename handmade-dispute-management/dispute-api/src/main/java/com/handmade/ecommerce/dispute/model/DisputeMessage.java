package com.handmade.ecommerce.dispute.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * DisputeMessage - Entity for messages in dispute conversation
 */
@Entity
@Table(name = "dispute_messages")
public class DisputeMessage {
    
    @Id
    @Column(name = "message_id", length = 50)
    private String messageId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispute_id", nullable = false)
    private Dispute dispute;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "sender_type", length = 20, nullable = false)
    private Dispute.SenderType senderType;
    
    @Column(name = "sender_id", length = 50)
    private String senderId;
    
    @Column(name = "message", columnDefinition = "TEXT", nullable = false)
    private String message;
    
    @Column(name = "is_internal")
    private Boolean isInternal; // Internal admin notes
    
    @Column(name = "sent_at")
    private Instant sentAt;
    
    @Column(name = "read_at")
    private Instant readAt;
    
    // Constructors
    public DisputeMessage() {
        this.sentAt = Instant.now();
        this.isInternal = false;
    }
    
    // Business methods
    public void markAsRead() {
        this.readAt = Instant.now();
    }
    
    public boolean isRead() {
        return readAt != null;
    }
    
    // Getters and Setters
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    
    public Dispute getDispute() { return dispute; }
    public void setDispute(Dispute dispute) { this.dispute = dispute; }
    
    public Dispute.SenderType getSenderType() { return senderType; }
    public void setSenderType(Dispute.SenderType senderType) { this.senderType = senderType; }
    
    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public Boolean getIsInternal() { return isInternal; }
    public void setIsInternal(Boolean isInternal) { this.isInternal = isInternal; }
    
    public Instant getSentAt() { return sentAt; }
    public void setSentAt(Instant sentAt) { this.sentAt = sentAt; }
    
    public Instant getReadAt() { return readAt; }
    public void setReadAt(Instant readAt) { this.readAt = readAt; }
}
