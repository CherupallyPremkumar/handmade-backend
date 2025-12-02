package com.handmade.ecommerce.razorpay.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "razorpay")
public class Razorpay extends BaseJpaEntity  {
    // Define your data model here
    public String attribute1;
}
