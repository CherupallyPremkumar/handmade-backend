package com.handmade.ecommerce.domain.cms;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_cms_entry")
public class CMSEntry extends AbstractJpaStateEntity {

    @Column(name = "schema_id", length = 36, nullable = false)
    private String schemaId;

    @Column(name = "slug", length = 255, nullable = false, unique = true)
    private String slug;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "status", length = 20)
    private String status = "DRAFT";

    @Column(name = "publish_start")
    private Date publishStart;

    @Column(name = "publish_end")
    private Date publishEnd;

    @Column(name = "active_version_id", length = 36)
    private String activeVersionId;
}
