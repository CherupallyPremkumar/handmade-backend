package com.handmade.ecommerce.domain.cms;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_cms_entry_version")
public class CMSEntryVersion extends BaseJpaEntity {

    @Column(name = "cms_entry_id", length = 36, nullable = false)
    private String cmsEntryId;

    @Column(name = "version_number", nullable = false)
    private Integer versionNumber;

    @Column(name = "field_values", columnDefinition = "TEXT")
    private String fieldValues; // JSON content

    @Column(name = "is_active")
    private Boolean isActive = false;
}
