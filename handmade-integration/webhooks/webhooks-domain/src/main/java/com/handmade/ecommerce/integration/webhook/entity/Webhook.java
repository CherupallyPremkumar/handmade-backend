package com.handmade.ecommerce.integration.webhook.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_webhook
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_webhook")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Webhook extends BaseJpaEntity {
    
    @Column(name = "target_url", nullable = false, length = 1000)
    private String targetUrl;
    @Column(name = "event_type", length = 100)
    private String eventType;
    @Column(name = "secret_key", length = 255)
    private String secretKey;
    @Column(name = "status", length = 20)
    private String status;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
