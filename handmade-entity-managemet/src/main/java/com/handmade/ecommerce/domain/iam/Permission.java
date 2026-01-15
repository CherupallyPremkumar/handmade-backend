package com.handmade.ecommerce.domain.iam;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * Permission - Access permission for specific operations
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_permission")
public class Permission extends BaseJpaEntity {

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "module", length = 50, nullable = false)
    private String module;

    // Relationships
    @ManyToMany(mappedBy = "permissions")
    private java.util.Set<Role> roles;
}
