package com.handmade.ecommerce.domain.experiment;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_experiment_bucket")
public class ExperimentBucket extends BaseJpaEntity {

    @Column(name = "experiment_id", length = 36, nullable = false)
    private String experimentId;

    @Column(name = "variant_key", length = 50, nullable = false)
    private String variantKey; // CONTROL, TREATMENT_A

    @Lob
    @Column(name = "payload_json")
    private String payloadJson;

    @Column(name = "allocation_weight")
    private Integer allocationWeight = 0;

    @Column(name = "is_control")
    private Boolean isControl = false;
}
