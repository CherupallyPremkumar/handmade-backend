package com.handmade.ecommerce.onboarding.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
@Entity
@Table(name = "onboarding")
public class Onboarding extends AbstractJpaStateEntity
{
	public String description;
}
