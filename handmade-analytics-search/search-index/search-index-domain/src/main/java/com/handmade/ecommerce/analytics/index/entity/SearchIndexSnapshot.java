package com.handmade.ecommerce.analytics.index.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_search_index_snapshot
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_search_index_snapshot")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SearchIndexSnapshot extends BaseJpaEntity {
    
    @Column(name = "index_name", nullable = false, length = 100)
    private String indexName;
    @Column(name = "business_version", nullable = false, length = 20)
    private String businessVersion;
    @Column(name = "status", length = 20)
    private String status;
    @Column(name = "document_count")
    private Long documentCount;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
