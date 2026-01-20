package com.handmade.ecommerce.cms.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_cms_entry_version")
public class CMSEntryVersion extends BaseJpaEntity {

    @Column(name = "entry_id", length = 36, nullable = false)
    private String entryId;

    @Column(name = "version_number", nullable = false)
    private Integer versionNumber;

    @Lob
    @Column(name = "content_json", columnDefinition = "TEXT")
    private String contentJson; // The actual content payload

    @Column(name = "commit_message", length = 500)
    private String commitMessage;

    @Column(name = "is_active")
    private Boolean isActive = false;
}
