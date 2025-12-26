package com.handmade.ecommerce.location.model;

import java.io.Serializable;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "hm_location", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"owner_type", "owner_id", "label"})
})
public class Location extends BaseJpaEntity  {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OwnerType ownerType;

    @Column(nullable = false)
    private Long ownerId;      // Id of the entity that owns this location

    @Column(length = 100)
    private String country;

    @Column(length = 100)
    private String state;

    @Column(length = 100)
    private String city;

    @Column(length = 20)
    private String zipcode;

    @Column(length = 255)
    private String addressLine1;

    @Column(length = 255)
    private String addressLine2;

    private Double latitude;
    private Double longitude;

    @Column(length = 100)
    private String label; // Optional: "Head Office", "Warehouse 1", "Home Address"

    // Optional: active/inactive for soft delete
    @Column(nullable = false)
    private Boolean active = true;

    public enum OwnerType {
        SELLER, CUSTOMER, WAREHOUSE
    }

}
