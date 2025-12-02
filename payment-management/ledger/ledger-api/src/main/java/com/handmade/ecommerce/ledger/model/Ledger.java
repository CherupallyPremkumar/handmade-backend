package com.handmade.ecommerce.ledger.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "ledger")
public class Ledger extends BaseJpaEntity  {
    // Define your data model here
    public String attribute1;
}
