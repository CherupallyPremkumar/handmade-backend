package com.handmade.ecommerce.domain.iam;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * RolePermission - Join table entity for Role-Permission relationship
 * Uses id as primary key with unique constraint on (role_id, permission_id)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_role_permission",
       uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "permission_id"}))
public class RolePermission extends BaseJpaEntity {

    @Column(name = "role_id", length = 36, nullable = false)
    private String roleId;

    @Column(name = "permission_id", length = 36, nullable = false)
    private String permissionId;
}
