package com.handmade.ecommerce.order.finance.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_settlement_batch
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_settlement_batch")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SettlementBatch extends BaseJpaEntity {
    
    @Column(name = "batch_reference", nullable = false, length = 100, unique = true)
    private String batchReference;
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    @Column(name = "start_time", nullable = false)
    private Date startTime;
    @Column(name = "close_time")
    private Date closeTime;
    @Column(name = "total_amount", precision = 12, scale = 2)
    private BigDecimal totalAmount;
    @Column(name = "transaction_count")
    private String transactionCount;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
