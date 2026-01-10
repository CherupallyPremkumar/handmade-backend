package com.handmade.ecommerce.analytics.behavior.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_browse_history
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_browse_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class BrowseHistory extends BaseJpaEntity {
    
    @Column(name = "user_id", length = 36)
    private String userId;
    @Column(name = "session_id", length = 100)
    private String sessionId;
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "viewed_at", nullable = false)
    private Date viewedAt;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
