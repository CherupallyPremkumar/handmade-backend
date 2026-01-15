package com.handmade.ecommerce.domain.search;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SearchRedirect - Configuration for search keyword redirects
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_search_redirect")
public class SearchRedirect extends BaseJpaEntity {

    @Column(name = "term", length = 100, nullable = false)
    private String term;

    @Column(name = "redirect_url", length = 255, nullable = false)
    private String redirectUrl;

    @Column(name = "match_type", length = 50)
    private String matchType; // EXACT, CONTAINS

    @Column(name = "start_date")
    private java.util.Date startDate;

    @Column(name = "end_date")
    private java.util.Date endDate;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
