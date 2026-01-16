package com.handmade.ecommerce.logistics.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * DeliveryAttempt - Tracks delivery attempts for a shipment
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_delivery_attempt")
public class DeliveryAttempt extends AbstractJpaStateEntity {

    @Column(name = "tracking_number", length = 100, nullable = false)
    private String trackingNumber;

    @Column(name = "attempt_time", nullable = false)
    private Date attemptTime;

    @Column(name = "outcome", length = 50)
    private String outcome; // SUCCESS, FAILED_NO_ACCESS

    @Column(name = "carrier_notes", length = 500)
    private String carrierNotes;

    @Column(name = "location_coordinates", length = 100)
    private String locationCoordinates;
}
