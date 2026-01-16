package com.handmade.ecommerce.cms.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_content_slot")
public class ContentSlot extends BaseJpaEntity {

    @Column(name = "slot_code", length = 100, nullable = false, unique = true)
    private String slotCode;

    @Column(name = "page_location", length = 100)
    private String pageLocation; // HOMEPAGE_HERO, SIDEBAR

    @Column(name = "content_page_id", length = 36)
    private String contentPageId;

    @Column(name = "cms_entry_id", length = 36)
    private String cmsEntryId;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
