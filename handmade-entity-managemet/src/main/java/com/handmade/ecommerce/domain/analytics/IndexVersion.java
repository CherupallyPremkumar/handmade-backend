package com.handmade.ecommerce.domain.analytics;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_search_index_version")
public class IndexVersion extends BaseJpaEntity {

    @Column(name = "index_name", length = 100, nullable = false)
    private String indexName;

    @Column(name = "index_version_tag", length = 50, nullable = false)
    private String indexVersionTag;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "created_at")
    private Date createdAt;
}
