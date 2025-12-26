package com.handmade.ecommerce.tax.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "tax")
public class Tax extends BaseJpaEntity  {
    // Define your data model here
    public String attribute1;
}
