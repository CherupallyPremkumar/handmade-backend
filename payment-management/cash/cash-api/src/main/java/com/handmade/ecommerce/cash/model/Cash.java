package com.handmade.ecommerce.cash.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "cash")
public class Cash extends BaseJpaEntity  {
    // Define your data model here
    public String attribute1;
}
