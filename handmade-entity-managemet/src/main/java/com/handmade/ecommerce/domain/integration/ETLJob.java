package com.handmade.ecommerce.domain.integration;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_etl_job")
public class ETLJob extends AbstractJpaStateEntity {

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "source_system", length = 100)
    private String sourceSystem;

    @Column(name = "status", length = 20)
    private String status; // Managed by STM

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "records_processed")
    private Long recordsProcessed;
}
