package com.handmade.ecommerce.domain.iam;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * UserRole - Join table entity for User-Role relationship with assignment tracking
 * Uses id as primary key with unique constraint on (user_id, role_id)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_user_role",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
public class UserRole extends BaseJpaEntity {

    @Column(name = "user_id", length = 36, nullable = false)
    private String userId;

    @Column(name = "role_id", length = 36, nullable = false)
    private String roleId;

    @Column(name = "assigned_by", length = 36)
    private String assignedBy;

    @Column(name = "assigned_at", nullable = false)
    private Date assignedAt;
}
