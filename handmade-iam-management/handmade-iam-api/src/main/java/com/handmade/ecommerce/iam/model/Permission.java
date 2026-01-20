package com.handmade.ecommerce.iam.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * Permission - Access permission for specific operations
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_permission")
public class Permission extends AbstractJpaStateEntity {

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "module", length = 50, nullable = false)
    private String module;

    // Relationships
    @ManyToMany(mappedBy = "permissions")
    private java.util.Set<Role> roles;
}
