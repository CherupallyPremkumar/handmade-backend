package com.handmade.ecommerce.limit.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_limit_definition")
public class LimitDefinition extends AbstractJpaStateEntity {

    @Column(name = "limit_key", length = 100, nullable = false, unique = true)
    private String limitKey;

    @Column(name = "resource_type", length = 50, nullable = false)
    private String resourceType; // PRODUCT, API, STORAGE

    @Column(name = "default_value")
    private Long defaultValue = 0L;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "reset_period", length = 20)
    private String resetPeriod; // MONTHLY, DAILY, REALTIME
}
