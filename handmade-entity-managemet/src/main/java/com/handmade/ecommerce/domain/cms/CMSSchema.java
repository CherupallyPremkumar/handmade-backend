package com.handmade.ecommerce.domain.cms;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_cms_schema")
public class CMSSchema extends BaseJpaEntity {

    @Column(name = "schema_code", length = 100, nullable = false, unique = true)
    private String schemaCode;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "schema_definition", columnDefinition = "TEXT")
    private String schemaDefinition;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
