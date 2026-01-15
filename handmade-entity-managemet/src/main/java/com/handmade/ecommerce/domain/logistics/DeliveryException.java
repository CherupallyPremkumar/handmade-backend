package com.handmade.ecommerce.domain.logistics;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_delivery_exception")
public class DeliveryException extends AbstractJpaStateEntity {

    @Column(name = "tracking_number", length = 100, nullable = false)
    private String trackingNumber;

    @Column(name = "exception_time", nullable = false)
    private Date exceptionTime;

    @Column(name = "exception_code", length = 50)
    private String exceptionCode; // WEATHER_DELAY, LOST_IN_TRANSIT, DAMAGED

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "resolution_status", length = 20)
    private String resolutionStatus; // Managed by STM
}
