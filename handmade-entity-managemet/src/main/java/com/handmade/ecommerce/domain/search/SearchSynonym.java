package com.handmade.ecommerce.domain.search;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SearchSynonym - Synonym definitions for search expansion
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_search_synonym")
public class SearchSynonym extends BaseJpaEntity {

    @Column(name = "term", length = 100, nullable = false)
    private String term;

    @Column(name = "synonyms", columnDefinition = "TEXT", nullable = false)
    private String synonyms; // Comma separated

    @Column(name = "is_one_way")
    private Boolean isOneWay = false;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
