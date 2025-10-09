package com.homebase.admin.dto;



public class NotificationStatsDTO {
    private Long unreadCount;
    private Long totalCount;

    public NotificationStatsDTO(Long unreadCount, Long totalCount) {
        this.unreadCount = unreadCount;
        this.totalCount = totalCount;
    }

    public Long getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Long unreadCount) {
        this.unreadCount = unreadCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
