package com.homebase.ecom.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.chenile.utils.entity.model.AbstractExtendedStateEntity;

@MappedSuperclass
public class MultiTenantExtendedStateEntity extends AbstractExtendedStateEntity {
    public String getSubTenantId() {
        return subTenantId;
    }

    public void setSubTenantId(String subTenantId) {
        this.subTenantId = subTenantId;
    }

    private String subTenantId;
}
