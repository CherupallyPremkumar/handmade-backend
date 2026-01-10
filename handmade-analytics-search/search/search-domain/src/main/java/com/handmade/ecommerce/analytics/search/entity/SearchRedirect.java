package com.handmade.ecommerce.analytics.search.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_search_redirect
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_search_redirect")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SearchRedirect extends BaseJpaEntity {
    
    @Column(name = "query_pattern", nullable = false, length = 500)
    private String queryPattern;
    @Column(name = "redirect_url", nullable = false, length = 2048)
    private String redirectUrl;
    @Column(name = "redirect_type", length = 50)
    private String redirectType;
    @Column(name = "priority")
    private String priority;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "usage_count")
    private String usageCount;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
