package com.handmade.ecommerce.domain.analytics;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * SearchTermReport - Analytics on search terms
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_search_term_report")
public class SearchTermReport extends BaseJpaEntity {

    @Column(name = "search_term", length = 255, nullable = false)
    private String searchTerm;

    @Column(name = "hit_count", nullable = false)
    private Long hitCount = 0L;

    @Column(name = "conversion_count", nullable = false)
    private Long conversionCount = 0L;

    @Column(name = "zero_result_count", nullable = false)
    private Long zeroResultCount = 0L;

    @Column(name = "report_period", length = 20, nullable = false)
    private String reportPeriod = "DAILY";

    @Column(name = "report_date", nullable = false)
    private Date reportDate;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;
}
