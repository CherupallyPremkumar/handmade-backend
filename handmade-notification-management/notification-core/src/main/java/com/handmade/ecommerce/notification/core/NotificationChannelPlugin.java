package com.handmade.ecommerce.notification.core;

import java.util.Map;

/**
 * NotificationChannelPlugin - Interface for all notification channel implementations
 * Enables plugin architecture for notifications (Email, SMS, Push, WhatsApp, etc.)
 */
public interface NotificationChannelPlugin {
    
    /**
     * Get channel code (e.g., "EMAIL", "SMS", "PUSH", "WHATSAPP")
     */
    String getChannelCode();
    
    /**
     * Get channel display name
     */
    String getChannelName();
    
    /**
     * Get channel description
     */
    String getDescription();
    
    /**
     * Check if channel is enabled
     */
    boolean isEnabled();
    
    /**
     * Send notification
     * 
     * @param recipient Recipient identifier (email, phone, device token, etc.)
     * @param subject Notification subject/title
     * @param message Notification message/body
     * @param metadata Additional metadata (template vars, attachments, etc.)
     * @return Notification result
     */
    NotificationResult send(String recipient, String subject, String message, Map<String, Object> metadata);
    
    /**
     * Send bulk notifications
     * 
     * @param recipients List of recipients
     * @param subject Subject
     * @param message Message
     * @param metadata Metadata
     * @return Bulk notification result
     */
    BulkNotificationResult sendBulk(String[] recipients, String subject, String message, Map<String, Object> metadata);
    
    /**
     * Validate recipient format
     * 
     * @param recipient Recipient identifier
     * @return true if valid for this channel
     */
    boolean isValidRecipient(String recipient);
    
    /**
     * Get channel priority (lower = higher priority)
     * Used for fallback scenarios
     */
    int getPriority();
    
    /**
     * Check if channel supports rich content (HTML, images, etc.)
     */
    boolean supportsRichContent();
    
    /**
     * Notification Result
     */
    class NotificationResult {
        private boolean success;
        private String messageId;
        private String status;
        private String errorMessage;
        
        public NotificationResult(boolean success, String messageId, String status, String errorMessage) {
            this.success = success;
            this.messageId = messageId;
            this.status = status;
            this.errorMessage = errorMessage;
        }
        
        public boolean isSuccess() { return success; }
        public String getMessageId() { return messageId; }
        public String getStatus() { return status; }
        public String getErrorMessage() { return errorMessage; }
    }
    
    /**
     * Bulk Notification Result
     */
    class BulkNotificationResult {
        private int totalSent;
        private int successCount;
        private int failureCount;
        private Map<String, String> failures;
        
        public BulkNotificationResult(int totalSent, int successCount, int failureCount, Map<String, String> failures) {
            this.totalSent = totalSent;
            this.successCount = successCount;
            this.failureCount = failureCount;
            this.failures = failures;
        }
        
        public int getTotalSent() { return totalSent; }
        public int getSuccessCount() { return successCount; }
        public int getFailureCount() { return failureCount; }
        public Map<String, String> getFailures() { return failures; }
    }
}
