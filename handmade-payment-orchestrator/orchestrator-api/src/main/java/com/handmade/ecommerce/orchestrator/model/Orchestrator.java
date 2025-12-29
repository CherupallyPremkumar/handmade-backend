package com.handmade.ecommerce.orchestrator.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Entity
@Table(name = "orchestrator")
public class Orchestrator extends BaseJpaEntity  {
    // Define your data model here
    public String attribute1;
}
