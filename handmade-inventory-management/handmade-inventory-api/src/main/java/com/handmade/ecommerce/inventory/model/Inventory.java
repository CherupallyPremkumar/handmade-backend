package com.handmade.ecommerce.inventory.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "inventory")
public class Inventory extends BaseJpaEntity  {
    // Define your data model here
    public String attribute1;
}
