package com.handmade.ecommerce.analytics.search.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_search_filter
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_search_filter")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SearchFilter extends BaseJpaEntity {
    
    @Column(name = "search_query_id", nullable = false, length = 36)
    private String searchQueryId;
    @Column(name = "filter_type", nullable = false, length = 100)
    private String filterType;
    @Column(name = "filter_key", nullable = false, length = 255)
    private String filterKey;
    @Column(name = "filter_value", nullable = false)
    private String filterValue;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
