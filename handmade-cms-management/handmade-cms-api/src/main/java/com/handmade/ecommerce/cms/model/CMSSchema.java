package com.handmade.ecommerce.cms.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_cms_schema")
public class CMSSchema extends BaseJpaEntity {

    @Column(name = "schema_code", length = 100, nullable = false, unique = true)
    private String schemaCode;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Lob
    @Column(name = "validation_json", columnDefinition = "TEXT")
    private String validationJson; // JSON Schema for content validation

    @Column(name = "is_active")
    private Boolean isActive = true;
}
