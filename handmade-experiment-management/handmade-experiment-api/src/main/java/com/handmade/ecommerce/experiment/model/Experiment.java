package com.handmade.ecommerce.experiment.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
@Entity
@Table(name = "experiment")
public class Experiment extends AbstractJpaStateEntity
{
	public String description;
}
