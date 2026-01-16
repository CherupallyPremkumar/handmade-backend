package com.handmade.ecommerce.domain.experiment;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_experiment_definition")
public class ExperimentDefinition extends AbstractJpaStateEntity {

    @Column(name = "experiment_key", length = 100, nullable = false, unique = true)
    private String experimentKey;

    @Column(name = "experiment_name")
    private String experimentName;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "type", length = 50, nullable = false)
    private String type; // AB_TEST, FEATURE_FLAG

    @Column(name = "owner_service", length = 100)
    private String ownerService;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;
}
