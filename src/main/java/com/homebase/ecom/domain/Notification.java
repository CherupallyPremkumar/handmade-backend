package com.homebase.ecom.domain;

import org.chenile.jpautils.entity.BaseJpaEntity;
import org.chenile.utils.entity.model.AbstractExtendedStateEntity;
import org.chenile.utils.entity.model.BaseEntity;


public class Notification extends BaseEntity {

    private NotificationType type;
    private String title;
    private String message;
    private Boolean read = false;

    private String link;

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public enum NotificationType {
        ORDER, PRODUCT, CATEGORY, SYSTEM
    }
}
