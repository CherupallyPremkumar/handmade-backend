package com.handmade.ecommerce.domain.limit;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_limit_counter")
public class LimitCounter extends AbstractJpaStateEntity {

    @Column(name = "entity_id", length = 100, nullable = false)
    private String entityId;

    @Column(name = "limit_key", length = 100, nullable = false)
    private String limitKey;

    @Column(name = "current_usage")
    private Long currentUsage = 0L;

    @Column(name = "reset_time")
    private Date resetTime;
}
