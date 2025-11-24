package com.handmade.ecommerce.events.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "events")
public class Events extends BaseJpaEntity  {
    // Define your data model here
    public String attribute1;
}
