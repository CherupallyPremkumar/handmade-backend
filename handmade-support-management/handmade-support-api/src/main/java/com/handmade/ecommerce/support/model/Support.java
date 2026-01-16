package com.handmade.ecommerce.support.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
@Entity
@Table(name = "support")
public class Support extends AbstractJpaStateEntity
{
	public String description;
}
