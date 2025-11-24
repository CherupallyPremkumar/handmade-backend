package com.handmade.ecommerce.inventory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Table(name = "hm_warehouse")
public class Warehouse extends AbstractJpaStateEntity {

    private String name;
    private String locationId;



}