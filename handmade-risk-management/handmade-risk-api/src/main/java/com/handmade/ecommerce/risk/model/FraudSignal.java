package com.handmade.ecommerce.risk.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_fraud_signal")
public class FraudSignal extends BaseJpaEntity {

    @Column(name = "signal_type", length = 100, nullable = false)
    private String signalType;

    @Column(name = "source_ip", length = 45)
    private String sourceIp;

    @Column(name = "device_fingerprint", length = 255)
    private String deviceFingerprint;

    @Column(name = "user_id", length = 100)
    private String userId;

    @Column(name = "score", precision = 5, scale = 2)
    private BigDecimal score;

    @Lob
    @Column(name = "metadata")
    private String metadata;

    @Column(name = "captured_at")
    private Date capturedAt;
}
