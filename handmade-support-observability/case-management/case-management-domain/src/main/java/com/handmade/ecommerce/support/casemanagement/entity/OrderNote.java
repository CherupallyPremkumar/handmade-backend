package com.handmade.ecommerce.support.casemanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_order_note
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_order_note")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class OrderNote extends BaseJpaEntity {
    
    @Column(name = "order_id", nullable = false, length = 36)
    private String orderId;
    @Column(name = "agent_id", length = 36)
    private String agentId;
    @Column(name = "note_text", nullable = false)
    private String noteText;
    @Column(name = "is_internal", nullable = false)
    private Boolean isInternal;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
