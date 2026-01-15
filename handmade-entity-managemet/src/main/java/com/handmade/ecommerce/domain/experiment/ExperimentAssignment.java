package com.handmade.ecommerce.domain.experiment;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_experiment_assignment")
public class ExperimentAssignment extends BaseJpaEntity {

    @Column(name = "experiment_key", length = 100, nullable = false)
    private String experimentKey;

    @Column(name = "variant_key", length = 50, nullable = false)
    private String variantKey;

    @Column(name = "entity_id", length = 100, nullable = false)
    private String entityId;

    @Column(name = "context_json", columnDefinition = "TEXT")
    private String contextJson;

    @Column(name = "assigned_at", nullable = false)
    private java.util.Date assignedAt;
}
