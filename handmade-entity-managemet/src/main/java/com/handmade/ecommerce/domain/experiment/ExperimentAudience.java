package com.handmade.ecommerce.domain.experiment;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_experiment_audience")
public class ExperimentAudience extends BaseJpaEntity {

    @Column(name = "experiment_id", length = 36, nullable = false)
    private String experimentId;

    @Column(name = "rule_name", length = 100)
    private String ruleName;

    @Lob
    @Column(name = "rule_json", nullable = false)
    private String ruleJson;

    @Column(name = "priority")
    private Integer priority = 0;
}
