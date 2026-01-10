package com.handmade.ecommerce.inventory.logistics.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_route_plan
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_route_plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RoutePlan extends BaseJpaEntity {
    
    @Column(name = "driver_id", nullable = false, length = 36)
    private String driverId;
    @Column(name = "vehicle_id", length = 36)
    private String vehicleId;
    @Column(name = "route_date", nullable = false)
    private Date routeDate;
    @Column(name = "planned_stops_json")
    private String plannedStopsJson;
    @Column(name = "status", length = 20)
    private String status;
    @Column(name = "estimated_distance_km", precision = 10, scale = 2)
    private BigDecimal estimatedDistanceKm;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
