package com.homebase.ecom.entity;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

@MappedSuperclass
public abstract class MultiTenantStateEntity extends AbstractJpaStateEntity {
    public String getSubTenantId() {
        return subTenantId;
    }

    public void setSubTenantId(String subTenantId) {
        this.subTenantId = subTenantId;
    }

    @Column(name = "sub_tenant_id", nullable = true)
    private String subTenantId;



}