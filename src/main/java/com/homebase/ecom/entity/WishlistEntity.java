package com.homebase.ecom.entity;

import com.homebase.ecom.entity.CustomerEntity;
import com.homebase.ecom.entity.WishlistItemEntity;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wishlists")
public class WishlistEntity extends AbstractJpaStateEntity {

     private String customerId;




}
