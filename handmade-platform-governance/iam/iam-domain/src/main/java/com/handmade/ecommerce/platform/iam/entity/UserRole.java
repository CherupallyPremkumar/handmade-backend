package com.handmade.ecommerce.platform.iam.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_user_role
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_user_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserRole extends BaseJpaEntity {
    
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;
    @Column(name = "role_id", nullable = false, length = 36)
    private String roleId;
    @Column(name = "assigned_by", length = 36)
    private String assignedBy;
    @Column(name = "assigned_at", nullable = false)
    private Date assignedAt;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
