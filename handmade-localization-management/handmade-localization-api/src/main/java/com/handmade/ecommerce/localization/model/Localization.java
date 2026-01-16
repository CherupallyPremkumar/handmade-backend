package com.handmade.ecommerce.localization.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "localization")
public class Localization extends BaseJpaEntity  {
    // Define your data model here
    public String attribute1;
}
