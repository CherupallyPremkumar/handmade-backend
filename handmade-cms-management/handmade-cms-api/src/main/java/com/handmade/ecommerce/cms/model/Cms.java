package com.handmade.ecommerce.cms.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
@Entity
@Table(name = "cms")
public class Cms extends AbstractJpaStateEntity
{
	public String description;
}
