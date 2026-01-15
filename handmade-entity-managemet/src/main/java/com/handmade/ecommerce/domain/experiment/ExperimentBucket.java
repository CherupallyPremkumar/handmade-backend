package com.handmade.ecommerce.domain.experiment;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_experiment_bucket")
public class ExperimentBucket extends BaseJpaEntity {

    @Column(name = "experiment_id", length = 36, nullable = false)
    private String experimentId;

    @Column(name = "variant_key", length = 50, nullable = false)
    private String variantKey; // CONTROL, TREATMENT_A

    @Column(name = "payload_json", columnDefinition = "TEXT")
    private String payloadJson;

    @Column(name = "allocation_weight")
    private Integer allocationWeight = 0;

    @Column(name = "is_control")
    private Boolean isControl = false;
}
