package com.handmade.ecommerce.customer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_customer_address
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_customer_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CustomerAddress extends BaseJpaEntity {
    
    @Column(name = "customer_id", nullable = false, length = 36)
    private String customerId;
    @Column(name = "address_type", length = 50)
    private String addressType;
    @Column(name = "is_default")
    private Boolean isDefault;
    @Column(name = "full_name", length = 255)
    private String fullName;
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
    @Column(name = "country_code", length = 3)
    private String countryCode;
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
