package com.handmade.ecommerce.platform.risk.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_fraud_signal
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_fraud_signal")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class FraudSignal extends BaseJpaEntity {
    
    @Column(name = "signal_type", nullable = false, length = 100)
    private String signalType;
    @Column(name = "source_ip", length = 45)
    private String sourceIp;
    @Column(name = "device_fingerprint", length = 255)
    private String deviceFingerprint;
    @Column(name = "user_id", length = 100)
    private String userId;
    @Column(name = "score", precision = 5, scale = 2)
    private BigDecimal score;
    @Column(name = "metadata")
    private String metadata;
    @Column(name = "captured_at")
    private Date capturedAt;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
