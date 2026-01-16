package com.handmade.ecommerce.seo.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "seo")
public class Seo extends BaseJpaEntity  {
    // Define your data model here
    public String attribute1;
}
