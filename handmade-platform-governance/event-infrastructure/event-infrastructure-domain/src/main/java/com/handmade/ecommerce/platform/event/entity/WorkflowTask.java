package com.handmade.ecommerce.platform.event.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_workflow_task
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_workflow_task")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class WorkflowTask extends BaseJpaEntity {
    
    @Column(name = "workflow_type", length = 100)
    private String workflowType;
    @Column(name = "instance_id", length = 36)
    private String instanceId;
    @Column(name = "step_name", length = 100)
    private String stepName;
    @Column(name = "status", length = 20)
    private String status;
    @Column(name = "input_data")
    private String inputData;
    @Column(name = "output_data")
    private String outputData;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
