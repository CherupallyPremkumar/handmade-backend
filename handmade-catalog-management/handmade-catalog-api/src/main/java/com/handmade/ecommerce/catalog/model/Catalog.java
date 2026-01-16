package com.handmade.ecommerce.catalog.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "catalog")
public class Catalog extends BaseJpaEntity  {
    // Define your data model here
    public String attribute1;
}
