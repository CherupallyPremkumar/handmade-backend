package com.handmade.ecommerce.search.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SearchResult - Captured significant search results (e.g. clicks)
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_search_result")
public class SearchResult extends BaseJpaEntity {

    @Column(name = "search_query_id", length = 36, nullable = false)
    private String searchQueryId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "result_position", nullable = false)
    private Integer resultPosition;

    @Column(name = "relevance_score", precision = 10, scale = 4)
    private java.math.BigDecimal relevanceScore;

    @Column(name = "was_clicked")
    private Boolean wasClicked = false;

    @Column(name = "click_timestamp")
    private java.util.Date clickTimestamp;
}
