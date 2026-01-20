package com.handmade.ecommerce.domain.analytics;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_ranking_signal")
public class RankingSignal extends BaseJpaEntity {

    @Column(name = "entity_id", length = 36, nullable = false)
    private String entityId;

    @Column(name = "signal_type", length = 50)
    private String signalType;

    @Column(name = "signal_weight", precision = 10, scale = 4)
    private BigDecimal signalWeight;
}
