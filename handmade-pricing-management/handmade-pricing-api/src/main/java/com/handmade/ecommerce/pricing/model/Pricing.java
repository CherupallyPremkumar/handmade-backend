package com.handmade.ecommerce.pricing.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
@Entity
@Table(name = "pricing")
public class Pricing extends AbstractJpaStateEntity
{
	public String description;
}
