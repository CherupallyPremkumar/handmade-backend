package com.handmade.ecommerce.domain.search;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SearchFilter - Configuration for faceted search filters
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_search_filter")
public class SearchFilter extends BaseJpaEntity {

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "display_name", length = 100, nullable = false)
    private String displayName;

    @Column(name = "filter_type", length = 50)
    private String filterType; // RANGE, TERMS, BOOLEAN

    @Column(name = "attribute_name", length = 100)
    private String attributeName;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "priority")
    private Integer priority = 0;
}
