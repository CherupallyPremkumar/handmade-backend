package com.handmade.ecommerce.limit.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
@Entity
@Table(name = "limit")
public class Limit extends AbstractJpaStateEntity
{
	public String description;
}
