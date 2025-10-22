package com.homebase.ecom.domain;

import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.util.Date;


public class TaxRate  extends MultiTenantBaseEntity{
    private String country;
    private String state;
    private String productCategory;
    private BigDecimal rate; // e.g., 0.07 for 7%
    private Date effectiveFrom;
    private Date effectiveTo;

    // getters and setters
}