package com.homebase.ecom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.chenile.jpautils.entity.BaseJpaEntity;

@MappedSuperclass
public class MultiTenantEntity extends BaseJpaEntity {
    public String getSubTenantId() {
        return subTenantId;
    }

    public void setSubTenantId(String subTenantId) {
        this.subTenantId = subTenantId;
    }

    @Column(name = "sub_tenant_id", nullable = true)
    private String subTenantId;
}
