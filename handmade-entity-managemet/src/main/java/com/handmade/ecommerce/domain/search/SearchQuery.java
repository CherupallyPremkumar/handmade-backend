package com.handmade.ecommerce.domain.search;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SearchQuery - Log of user search queries for analytics
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_search_query")
public class SearchQuery extends BaseJpaEntity {

    @Column(name = "query_text", length = 255, nullable = false)
    private String queryText;

    @Column(name = "customer_id", length = 36)
    private String customerId;

    @Column(name = "session_id", length = 100)
    private String sessionId;

    @Column(name = "result_count")
    private Integer resultCount;

    @Column(name = "execution_time_ms")
    private Long executionTimeMs;

    @Column(name = "filters_applied", columnDefinition = "TEXT")
    private String filtersApplied;
}
