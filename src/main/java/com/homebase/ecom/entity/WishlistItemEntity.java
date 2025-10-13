package com.homebase.ecom.entity;

import com.homebase.ecom.entity.ProductEntity;
import com.homebase.ecom.entity.WishlistEntity;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Table(name = "wishlist_items")
public class WishlistItemEntity extends BaseJpaEntity {

    private String wishlistId;

    private String productId;

    private Integer desiredQuantity; // optional

}
