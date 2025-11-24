package com.handmade.ecommerce.notification.model;

import org.chenile.workflow.activities.model.ActivityLog;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;
@Entity
@Table(name = "notification_activity")
public class NotificationActivityLog extends BaseJpaEntity implements ActivityLog{
    public String activityName;
    public boolean activitySuccess;
    public String activityComment;
    @Override
    public String getName() {
        return activityName;
    }

    @Override
    public boolean getSuccess() {
        return activitySuccess;
    }

    @Override
    public String getComment() {
        return activityComment;
    }
}
