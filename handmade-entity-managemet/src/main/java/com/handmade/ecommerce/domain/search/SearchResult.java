package com.handmade.ecommerce.domain.search;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SearchResult - Captured significant search results (e.g. clicks)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_search_result")
public class SearchResult extends BaseJpaEntity {

    @Column(name = "search_query_id", length = 36, nullable = false)
    private String searchQueryId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "rank_position")
    private Integer rankPosition;

    @Column(name = "score")
    private Double score;

    @Column(name = "is_clicked")
    private Boolean isClicked = false;
}
