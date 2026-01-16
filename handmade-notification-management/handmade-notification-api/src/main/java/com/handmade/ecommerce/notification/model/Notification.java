package com.handmade.ecommerce.notification.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
@Entity
@Table(name = "notification")
public class Notification extends AbstractJpaStateEntity
{
	public String description;
}
