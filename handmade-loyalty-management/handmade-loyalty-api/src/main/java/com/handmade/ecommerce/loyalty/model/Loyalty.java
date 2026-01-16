package com.handmade.ecommerce.loyalty.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "loyalty")
public class Loyalty extends BaseJpaEntity  {
    // Define your data model here
    public String attribute1;
}
