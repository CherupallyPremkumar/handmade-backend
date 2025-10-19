package com.homebase.ecom.entity;

import com.homebase.ecom.entity.CustomerEntity;
import com.homebase.ecom.entity.WishlistItemEntity;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hm_wishlist")
public class WishlistEntity extends AbstractJpaStateEntity {

     private String customerId;
     private String name;
     private String description;


     public String getCustomerId() {
          return customerId;
     }

     public void setCustomerId(String customerId) {
          this.customerId = customerId;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getDescription() {
          return description;
     }

     public void setDescription(String description) {
          this.description = description;
     }
}
