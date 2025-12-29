package com.handmade.ecommerce.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity

@Setter
@Getter
public class Region {
    @Id
    private String code; // e.g., US-EAST, AP-SOUTH
    private String name; // e.g., "US East", "India South"
}
