package com.homebase.ecom.entity;

import com.homebase.ecom.constants.HeaderConstants;
import jakarta.persistence.*;
import org.chenile.core.context.ContextContainer;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * Base entity class for multi-tenant state entities.
 * Extends AbstractJpaStateEntity and adds sub-tenant support.
 */

@MappedSuperclass
public abstract class MultiTenantStateEntity extends AbstractJpaStateEntity {

    public void setSubTenantId(String subTenantId) {
        this.subTenantId = subTenantId;
    }

    @Column(name = "sub_tenant_id", nullable = true)
    private String subTenantId;

    public String getSubTenantId() {
        return subTenantId;
    }

    @Override
    protected void initializeIfRequired() {
        super.initializeIfRequired();
        ContextContainer contextContainer = ContextContainer.getInstance();
        if (this.subTenantId == null) {
            this.subTenantId = contextContainer.get(HeaderConstants.HEADER_SUB_TENANT_ID);
        }
    }
}