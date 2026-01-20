package com.handmade.ecommerce.domain.catalog;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * Collection - Curated product collections (e.g., "Summer Sale", "New
 * Arrivals")
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_collection")
public class Collection extends BaseJpaEntity {

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "slug", length = 255, nullable = false, unique = true)
    private String slug;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "type", length = 50, nullable = false)
    private String type;

    @Column(name = "image_url", length = 2048)
    private String imageUrl;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "featured")
    private Boolean featured = false;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "auto_update")
    private Boolean autoUpdate = false;

    @Lob
    @Column(name = "rule_expression", columnDefinition = "TEXT")
    private String ruleExpression;

    @Column(name = "product_count")
    private Long productCount = 0L;
}
