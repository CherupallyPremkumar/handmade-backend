package com.handmade.ecommerce.policy.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
@Entity
@Table(name = "policy")
public class Policy extends AbstractJpaStateEntity
{
	public String description;
}
