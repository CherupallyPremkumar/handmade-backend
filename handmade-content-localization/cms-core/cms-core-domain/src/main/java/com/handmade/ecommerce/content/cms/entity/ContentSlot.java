package com.handmade.ecommerce.content.cms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_content_slot
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_content_slot")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ContentSlot extends BaseJpaEntity {
    
    @Column(name = "slot_key", nullable = false, length = 100, unique = true)
    private String slotKey;
    @Column(name = "active_asset_id", length = 36)
    private String activeAssetId;
    @Column(name = "priority")
    private String priority;
    @Column(name = "is_active")
    private Boolean isActive;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
