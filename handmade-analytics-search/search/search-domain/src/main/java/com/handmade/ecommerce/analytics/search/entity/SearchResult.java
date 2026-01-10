package com.handmade.ecommerce.analytics.search.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_search_result
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_search_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SearchResult extends BaseJpaEntity {
    
    @Column(name = "search_query_id", nullable = false, length = 36)
    private String searchQueryId;
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "result_position", nullable = false)
    private String resultPosition;
    @Column(name = "relevance_score", precision = 10, scale = 4)
    private BigDecimal relevanceScore;
    @Column(name = "was_clicked")
    private Boolean wasClicked;
    @Column(name = "click_timestamp")
    private Date clickTimestamp;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
