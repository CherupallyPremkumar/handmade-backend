package com.handmade.ecommerce.platform.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;
import org.chenile.workflow.activities.model.ActivityLog;
import java.io.Serializable;

/**
 * Platform Activity Log - PURE DOMAIN MODEL
 */
@Entity
@Table(name = "hm_platform_activity_log")
public class PlatformActivityLog extends BaseJpaEntity implements ActivityLog, Serializable {
    private String id;
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
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
