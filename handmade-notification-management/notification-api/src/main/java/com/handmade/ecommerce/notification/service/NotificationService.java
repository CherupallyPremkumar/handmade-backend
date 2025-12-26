package com.handmade.ecommerce.notification.service;

import com.handmade.ecommerce.command.notification.NotificationResponse;
import com.handmade.ecommerce.command.notification.SendNotificationRequest;

import java.util.List;

/**
 * Notification Service Interface
 * Used by both implementation and Chenile Proxy
 */
public interface NotificationService {

    /**
     * Send a notification to a user
     * // * @param request Notification details
     * //
     */
    void sendNotification(SendNotificationRequest request);

    /**
     * Get notifications for a user
     * 
     * @param userId User identifier
     * @return List of notifications
     */
    List<NotificationResponse> getNotifications(Long userId);

    /**
     * Mark notification as read
     * 
     * @param notificationId Notification identifier
     */
    void markAsRead(Long notificationId);
}
