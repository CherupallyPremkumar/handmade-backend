package com.homebase.admin.service;

import com.homebase.admin.dto.NotificationDTO;
import com.homebase.admin.dto.NotificationStatsDTO;
import com.homebase.admin.entity.Notification;
import com.homebase.admin.config.TenantContext;
import com.homebase.admin.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationDTO> getAllNotifications() {
        String tenantId = TenantContext.getCurrentTenant();
        return notificationRepository.findByTenantIdOrderByCreatedAtDesc(tenantId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public NotificationStatsDTO getStats() {
        String tenantId = TenantContext.getCurrentTenant();
        Long unreadCount = notificationRepository.countByTenantIdAndReadFalse(tenantId);
        Long totalCount = (long) notificationRepository.findByTenantIdOrderByCreatedAtDesc(tenantId).size();
        return new NotificationStatsDTO(unreadCount, totalCount);
    }

    @Transactional
    public void markAsRead(Long id) {
        String tenantId = TenantContext.getCurrentTenant();
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        
        if (!notification.getTenantId().equals(tenantId)) {
            throw new RuntimeException("Access denied");
        }
        
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Transactional
    public void markAllAsRead() {
        String tenantId = TenantContext.getCurrentTenant();
        List<Notification> notifications = notificationRepository.findByTenantIdOrderByCreatedAtDesc(tenantId);
        notifications.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(notifications);
    }

    @Transactional
    public NotificationDTO createNotification(NotificationDTO dto) {
        Notification notification = convertToEntity(dto);
        Notification saved = notificationRepository.save(notification);
        return convertToDTO(saved);
    }

    private NotificationDTO convertToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(String.valueOf(notification.getId()));
        dto.setType(notification.getType().name().toLowerCase());
        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setRead(notification.getRead());
        dto.setCreatedAt(notification.getCreatedAt() != null ? notification.getCreatedAt().toString() : null);
        dto.setLink(notification.getLink());
        return dto;
    }

    private Notification convertToEntity(NotificationDTO dto) {
        Notification notification = new Notification();
        notification.setType(Notification.NotificationType.valueOf(dto.getType().toUpperCase()));
        notification.setTitle(dto.getTitle());
        notification.setMessage(dto.getMessage());
        notification.setRead(dto.getRead() != null ? dto.getRead() : false);
        notification.setLink(dto.getLink());
        return notification;
    }
}
