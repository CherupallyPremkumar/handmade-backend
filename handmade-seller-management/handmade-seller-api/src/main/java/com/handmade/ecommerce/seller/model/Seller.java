package com.handmade.ecommerce.seller.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
@Entity
@Table(name = "seller")
public class Seller extends AbstractJpaStateEntity
{
	public String description;
}
