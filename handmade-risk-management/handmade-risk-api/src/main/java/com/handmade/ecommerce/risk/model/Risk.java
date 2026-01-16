package com.handmade.ecommerce.risk.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
@Entity
@Table(name = "risk")
public class Risk extends AbstractJpaStateEntity
{
	public String description;
}
