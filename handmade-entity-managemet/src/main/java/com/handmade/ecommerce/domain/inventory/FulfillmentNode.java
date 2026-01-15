package com.handmade.ecommerce.domain.inventory;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * FulfillmentNode - Warehouse/fulfillment center location
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_fulfillment_node")
public class FulfillmentNode extends BaseJpaEntity {

    @Column(name = "node_code", length = 50, nullable = false, unique = true)
    private String nodeCode;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "node_type", length = 50)
    private String nodeType; // WAREHOUSE, DARK_STORE, PICKUP_POINT

    @Column(name = "address_line1", length = 255)
    private String addressLine1;

    @Column(name = "address_line2", length = 255)
    private String addressLine2;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "country", length = 2)
    private String country;

    @Column(name = "latitude", precision = 10, scale = 7)
    private java.math.BigDecimal latitude;

    @Column(name = "longitude", precision = 10, scale = 7)
    private java.math.BigDecimal longitude;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
