package com.handmade.ecommerce.domain.customer;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * WishlistItem - Items in customer wishlist
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_wishlist_item")
public class WishlistItem extends BaseJpaEntity {

    @Column(name = "wishlist_id", length = 36, nullable = false)
    private String wishlistId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "added_date", nullable = false)
    private Date addedDate;

    @Column(name = "priority", length = 50)
    private String priority; // LOW, MEDIUM, HIGH

    @Column(name = "quantity_desired")
    private Integer quantityDesired = 1;
}
