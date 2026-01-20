package com.handmade.ecommerce.domain.search;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SearchSynonym - Synonym definitions for search expansion
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_search_synonym")
public class SearchSynonym extends BaseJpaEntity {

    @Column(name = "term", length = 255, nullable = false)
    private String term;

    @Column(name = "synonym", length = 255, nullable = false)
    private String synonym;

    @Column(name = "synonym_type", length = 50)
    private String synonymType = "TWO_WAY"; // TWO_WAY, ONE_WAY

    @Column(name = "weight", precision = 5, scale = 2)
    private java.math.BigDecimal weight = java.math.BigDecimal.valueOf(1.0);

    @Column(name = "is_active")
    private Boolean isActive = true;
}
