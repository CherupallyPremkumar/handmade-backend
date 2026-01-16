package com.handmade.ecommerce.notification.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
class UserPreferenceKey implements Serializable {
    @Column(name = "user_id") private String userId;
    @Column(name = "notification_type") private String notificationType;
    @Column(name = "channel") private String channel;
}

@Entity
@Table(name = "hm_user_preference")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreference {

    @EmbeddedId
    private UserPreferenceKey key;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;
}