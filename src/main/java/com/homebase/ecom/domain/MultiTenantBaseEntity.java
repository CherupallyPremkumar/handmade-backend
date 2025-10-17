package com.homebase.ecom.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.chenile.utils.entity.model.BaseEntity;

@MappedSuperclass
public class MultiTenantBaseEntity extends BaseEntity {
    public String getSubTenantId() {
        return subTenantId;
    }

    public void setSubTenantId(String subTenantId) {
        this.subTenantId = subTenantId;
    }
    private String subTenantId;
}
