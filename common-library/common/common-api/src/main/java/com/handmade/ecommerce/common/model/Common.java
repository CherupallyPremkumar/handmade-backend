package com.handmade.ecommerce.common.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "common")
public class Common extends BaseJpaEntity  {
    // Define your data model here
    public String attribute1;
}
