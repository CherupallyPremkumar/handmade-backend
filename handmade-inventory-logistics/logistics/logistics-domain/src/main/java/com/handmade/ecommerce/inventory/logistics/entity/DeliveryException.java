package com.handmade.ecommerce.inventory.logistics.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_delivery_exception
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_delivery_exception")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class DeliveryException extends BaseJpaEntity {
    
    @Column(name = "tracking_number", nullable = false, length = 100)
    private String trackingNumber;
    @Column(name = "exception_time", nullable = false)
    private Date exceptionTime;
    @Column(name = "exception_code", length = 50)
    private String exceptionCode;
    @Column(name = "description", length = 500)
    private String description;
    @Column(name = "resolution_status", length = 20)
    private String resolutionStatus;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
