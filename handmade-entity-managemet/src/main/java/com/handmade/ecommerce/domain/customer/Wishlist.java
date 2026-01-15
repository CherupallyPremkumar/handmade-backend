package com.handmade.ecommerce.domain.customer;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * Wishlist - Customer wishlist
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_wishlist")
public class Wishlist extends BaseJpaEntity {

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "is_public")
    private Boolean isPublic = false;

    @Column(name = "is_default")
    private Boolean isDefault = false;
}
