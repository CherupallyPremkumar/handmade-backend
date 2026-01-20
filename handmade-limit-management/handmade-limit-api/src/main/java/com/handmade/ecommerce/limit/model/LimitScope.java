package com.handmade.ecommerce.limit.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_limit_scope")
public class LimitScope extends BaseJpaEntity {

    @Column(name = "limit_definition_id", length = 36, nullable = false)
    private String limitDefinitionId;

    @Column(name = "scope_dimension", length = 50, nullable = false)
    private String scopeDimension; // SELLER_TIER, USER_ROLE

    @Column(name = "scope_value", length = 255, nullable = false)
    private String scopeValue; // GOLD, ADMIN

    @Column(name = "override_value")
    private Long overrideValue;
}
