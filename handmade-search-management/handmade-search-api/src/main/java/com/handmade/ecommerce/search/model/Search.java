package com.handmade.ecommerce.search.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "search")
public class Search extends BaseJpaEntity  {
    // Define your data model here
    public String attribute1;
}
