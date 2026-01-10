package com.handmade.ecommerce.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_fulfillment_node
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_fulfillment_node")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class FulfillmentNode extends BaseJpaEntity {
    
    @Column(name = "platform_id", nullable = false, length = 36)
    private String platformId;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "type", nullable = false, length = 50)
    private String type;
    @Column(name = "address_line1", length = 255)
    private String addressLine1;
    @Column(name = "city", length = 100)
    private String city;
    @Column(name = "state_code", length = 10)
    private String stateCode;
    @Column(name = "country_code", length = 3)
    private String countryCode;
    @Column(name = "postal_code", length = 20)
    private String postalCode;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
