package com.handmade.ecommerce.event.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_job_scheduler")
public class JobScheduler extends BaseJpaEntity {

    @Column(name = "job_name", length = 100, nullable = false)
    private String jobName;

    @Column(name = "cron_expression", length = 50)
    private String cronExpression;

    @Column(name = "last_run_time")
    private Date lastRunTime;

    @Column(name = "next_run_time")
    private Date nextRunTime;

    @Column(name = "status", length = 20)
    private String status; // ACTIVE, PAUSED, RUNNING
}
