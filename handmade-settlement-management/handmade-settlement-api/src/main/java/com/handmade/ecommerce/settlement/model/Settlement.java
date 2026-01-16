package com.handmade.ecommerce.settlement.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
@Entity
@Table(name = "settlement")
public class Settlement extends AbstractJpaStateEntity
{
	public String description;
}
