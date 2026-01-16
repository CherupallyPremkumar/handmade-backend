package com.handmade.ecommerce.domain.search;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SearchRedirect - Configuration for search keyword redirects
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_search_redirect")
public class SearchRedirect extends BaseJpaEntity {

    @Column(name = "query_pattern", length = 500, nullable = false)
    private String queryPattern;

    @Column(name = "redirect_url", length = 2048, nullable = false)
    private String redirectUrl;

    @Column(name = "redirect_type", length = 50)
    private String redirectType = "PERMANENT"; // PERMANENT, TEMPORARY

    @Column(name = "priority")
    private Integer priority = 0;

    @Column(name = "start_date")
    private java.util.Date startDate;

    @Column(name = "end_date")
    private java.util.Date endDate;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "usage_count")
    private Integer usageCount = 0;
}
