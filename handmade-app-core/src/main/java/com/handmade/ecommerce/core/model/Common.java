package com.handmade.ecommerce.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "common")
public class Common extends BaseJpaEntity  {
    // Define your data model here
    public String attribute1;
}
