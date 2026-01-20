package com.handmade.ecommerce.event.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_workflow_task")
public class WorkflowTask extends AbstractJpaStateEntity {

    @Column(name = "workflow_type", length = 100)
    private String workflowType;

    @Column(name = "instance_id", length = 36)
    private String instanceId;

    @Column(name = "step_name", length = 100)
    private String stepName;

    @Lob
    @Column(name = "input_data", columnDefinition = "TEXT")
    private String inputData;

    @Lob
    @Column(name = "output_data", columnDefinition = "TEXT")
    private String outputData;
}
