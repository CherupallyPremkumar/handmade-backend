package com.handmade.ecommerce.analytics.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_search_index_version")
public class IndexVersion extends BaseJpaEntity {

    @Column(name = "index_name", length = 100, nullable = false)
    private String indexName;

    @Column(name = "index_version", length = 50, nullable = false)
    private String indexVersion;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "created_at")
    private Date createdAt;
}
