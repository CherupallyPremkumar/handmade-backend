package com.handmade.ecommerce.analytics.search.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_search_synonym
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_search_synonym")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SearchSynonym extends BaseJpaEntity {
    
    @Column(name = "term", nullable = false, length = 255)
    private String term;
    @Column(name = "synonym", nullable = false, length = 255)
    private String synonym;
    @Column(name = "synonym_type", length = 50)
    private String synonymType;
    @Column(name = "weight", precision = 5, scale = 2)
    private BigDecimal weight;
    @Column(name = "is_active")
    private Boolean isActive;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
