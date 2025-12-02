package com.handmade.ecommerce.stripe.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "stripe")
public class Stripe extends BaseJpaEntity  {
    // Define your data model here
    public String attribute1;
}
