package com.handmade.ecommerce.seller.performance.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_seller_action_item
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_seller_action_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SellerActionItem extends BaseJpaEntity {
    
    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;
    @Column(name = "action_type", nullable = false, length = 50)
    private String actionType;
    @Column(name = "description", length = 500)
    private String description;
    @Column(name = "due_date")
    private Date dueDate;
    @Column(name = "status", length = 20)
    private String status;
    @Column(name = "severity", length = 20)
    private String severity;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
