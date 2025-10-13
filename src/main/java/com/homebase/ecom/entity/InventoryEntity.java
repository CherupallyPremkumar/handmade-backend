package com.homebase.ecom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * Inventory entity with state management.
 */
@Entity
@Table(name = "inventory")
public class InventoryEntity extends AbstractJpaStateEntity {

    // Additional fields and methods can be added here
}
