package com.handmade.ecommerce.catalog.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_collection
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_collection")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Collection extends BaseJpaEntity {
    
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "slug", nullable = false, length = 255, unique = true)
    private String slug;
    @Column(name = "description")
    private String description;
    @Column(name = "type", nullable = false, length = 50)
    private String type;
    @Column(name = "image_url", length = 2048)
    private String imageUrl;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "featured")
    private Boolean featured;
    @Column(name = "display_order")
    private String displayOrder;
    @Column(name = "auto_update")
    private Boolean autoUpdate;
    @Column(name = "rule_expression")
    private String ruleExpression;
    @Column(name = "product_count")
    private Long productCount;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
