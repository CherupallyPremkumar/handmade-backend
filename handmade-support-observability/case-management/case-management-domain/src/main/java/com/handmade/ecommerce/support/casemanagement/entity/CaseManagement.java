package com.handmade.ecommerce.support.casemanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_case_management
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_case_management")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CaseManagement extends BaseJpaEntity {
    
    @Column(name = "case_type", nullable = false, length = 50)
    private String caseType;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Column(name = "priority", nullable = false, length = 20)
    private String priority;
    @Column(name = "subject", nullable = false, length = 255)
    private String subject;
    @Column(name = "description")
    private String description;
    @Column(name = "created_by_user_id", nullable = false, length = 36)
    private String createdByUserId;
    @Column(name = "assigned_to", length = 36)
    private String assignedTo;
    @Column(name = "related_entity_id", length = 36)
    private String relatedEntityId;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
