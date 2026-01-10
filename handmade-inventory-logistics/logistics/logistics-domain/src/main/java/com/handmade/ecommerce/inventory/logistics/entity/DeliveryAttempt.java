package com.handmade.ecommerce.inventory.logistics.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_delivery_attempt
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_delivery_attempt")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class DeliveryAttempt extends BaseJpaEntity {
    
    @Column(name = "tracking_number", nullable = false, length = 100)
    private String trackingNumber;
    @Column(name = "attempt_time", nullable = false)
    private Date attemptTime;
    @Column(name = "outcome", length = 50)
    private String outcome;
    @Column(name = "carrier_notes", length = 500)
    private String carrierNotes;
    @Column(name = "location_coordinates", length = 100)
    private String locationCoordinates;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
