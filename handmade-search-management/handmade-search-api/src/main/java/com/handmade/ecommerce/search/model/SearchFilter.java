package com.handmade.ecommerce.search.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SearchFilter - Configuration for faceted search filters
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_search_filter")
public class SearchFilter extends BaseJpaEntity {

    @Column(name = "search_query_id", length = 36, nullable = false)
    private String searchQueryId;

    @Column(name = "filter_type", length = 100, nullable = false)
    private String filterType; // RANGE, TERMS, BOOLEAN

    @Column(name = "filter_key", length = 255, nullable = false)
    private String filterKey;

    @Lob
    @Column(name = "filter_value", nullable = false)
    private String filterValue;
}
