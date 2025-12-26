package com.handmade.ecommerce.reconciliation.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "reconciliation")
public class Reconciliation extends BaseJpaEntity  {
    // Define your data model here
    public String attribute1;
}
