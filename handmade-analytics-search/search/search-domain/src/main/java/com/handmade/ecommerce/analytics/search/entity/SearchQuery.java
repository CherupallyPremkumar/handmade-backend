package com.handmade.ecommerce.analytics.search.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_search_query
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_search_query")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SearchQuery extends BaseJpaEntity {
    
    @Column(name = "customer_id", length = 36)
    private String customerId;
    @Column(name = "session_id", length = 255)
    private String sessionId;
    @Column(name = "query_text", nullable = false)
    private String queryText;
    @Column(name = "normalized_query")
    private String normalizedQuery;
    @Column(name = "result_count")
    private String resultCount;
    @Column(name = "search_context", length = 100)
    private String searchContext;
    @Column(name = "browse_node_id", length = 36)
    private String browseNodeId;
    @Column(name = "clicked_product_id", length = 36)
    private String clickedProductId;
    @Column(name = "clicked_position")
    private String clickedPosition;
    @Column(name = "converted_to_order")
    private Boolean convertedToOrder;
    @Column(name = "order_id", length = 36)
    private String orderId;
    @Column(name = "search_timestamp", nullable = false)
    private Date searchTimestamp;
    @Column(name = "response_time_ms")
    private String responseTimeMs;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
