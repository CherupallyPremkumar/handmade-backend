package com.handmade.ecommerce.domain.search;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SearchQuery - Log of user search queries for analytics
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_search_query")
public class SearchQuery extends BaseJpaEntity {

    @Lob
    @Column(name = "query_text", nullable = false)
    private String queryText;

    @Column(name = "customer_id", length = 36)
    private String customerId;

    @Column(name = "session_id", length = 255)
    private String sessionId;

    @Lob
    @Column(name = "normalized_query")
    private String normalizedQuery;

    @Column(name = "result_count")
    private Integer resultCount = 0;

    @Column(name = "search_context", length = 100)
    private String searchContext;

    @Column(name = "browse_node_id", length = 36)
    private String browseNodeId;

    @Column(name = "clicked_product_id", length = 36)
    private String clickedProductId;

    @Column(name = "clicked_position")
    private Integer clickedPosition;

    @Column(name = "converted_to_order")
    private Boolean convertedToOrder = false;

    @Column(name = "order_id", length = 36)
    private String orderId;

    @Column(name = "search_timestamp", nullable = false)
    private java.util.Date searchTimestamp;

    @Column(name = "response_time_ms")
    private Integer responseTimeMs;
}
