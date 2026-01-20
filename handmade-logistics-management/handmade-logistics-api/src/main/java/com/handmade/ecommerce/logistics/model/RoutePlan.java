package com.handmade.ecommerce.logistics.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * RoutePlan - Delivery route planning for drivers
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_route_plan")
public class RoutePlan extends AbstractJpaStateEntity {

    @Column(name = "driver_id", length = 36, nullable = false)
    private String driverId;

    @Column(name = "vehicle_id", length = 36)
    private String vehicleId;

    @Temporal(TemporalType.DATE)
    @Column(name = "route_date", nullable = false)
    private Date routeDate;

    @Lob
    @Column(name = "planned_stops_json")
    private String plannedStopsJson;

    @Column(name = "status", length = 20)
    private String status = "PLANNED"; // PLANNED, IN_PROGRESS

    @Column(name = "estimated_distance_km", precision = 10, scale = 2)
    private BigDecimal estimatedDistanceKm;
}
