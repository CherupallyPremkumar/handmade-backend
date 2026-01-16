package com.handmade.ecommerce.observability.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "observability")
public class Observability extends BaseJpaEntity  {
    // Define your data model here
    public String attribute1;
}
