package com.handmade.ecommerce.customer.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * Wishlist - Customer wishlist
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_wishlist")
public class Wishlist extends BaseJpaEntity {

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "privacy_level", length = 50)
    private String privacyLevel; // PUBLIC, PRIVATE, SHARED
}
