package com.handmade.ecommerce.domain.analytics;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_search_index_snapshot")
public class SearchIndexSnapshot extends BaseJpaEntity {

    @Column(name = "index_name", length = 100, nullable = false)
    private String indexName;

    @Column(name = "business_version", length = 20, nullable = false)
    private String businessVersion;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "document_count")
    private Long documentCount;
}
