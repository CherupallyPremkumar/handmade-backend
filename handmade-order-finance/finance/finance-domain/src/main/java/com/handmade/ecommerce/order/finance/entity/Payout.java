package com.handmade.ecommerce.order.finance.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_payout
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_payout")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Payout extends BaseJpaEntity {
    
    @Column(name = "seller_id", length = 36)
    private String sellerId;
    @Column(name = "amount", precision = 19, scale = 4)
    private BigDecimal amount;
    @Column(name = "status", length = 50)
    private String status;
    @Column(name = "bank_reference_id", length = 100)
    private String bankReferenceId;
    @Column(name = "payout_date")
    private Date payoutDate;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
