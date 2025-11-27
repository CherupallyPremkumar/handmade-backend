package com.handmade.ecommerce.core.model;

import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Table(name = "hsn_code")
public class HSNCode extends BaseJpaEntity {

    @Column(nullable = false, unique = true, length = 10)
    private String code; // e.g., "100630"

    @Column(nullable = false)
    private String description; // e.g., "Rice"

    @Column(nullable = false)
    private String country; // e.g., "IN" for India


    private boolean active = true; // soft delete / enable/disable

    // getters and setters
}