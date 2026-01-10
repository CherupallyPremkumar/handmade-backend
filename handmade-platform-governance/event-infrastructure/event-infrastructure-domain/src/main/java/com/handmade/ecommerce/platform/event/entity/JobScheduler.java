package com.handmade.ecommerce.platform.event.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_job_scheduler
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_job_scheduler")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class JobScheduler extends BaseJpaEntity {
    
    @Column(name = "job_name", nullable = false, length = 100)
    private String jobName;
    @Column(name = "cron_expression", length = 50)
    private String cronExpression;
    @Column(name = "last_run_time")
    private Date lastRunTime;
    @Column(name = "next_run_time")
    private Date nextRunTime;
    @Column(name = "status", length = 20)
    private String status;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
