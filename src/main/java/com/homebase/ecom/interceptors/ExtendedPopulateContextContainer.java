package com.homebase.ecom.interceptors;

import com.homebase.ecom.constants.HeaderConstants;
import org.chenile.core.context.ChenileExchange;
import org.chenile.core.context.ContextContainer;
import org.chenile.core.context.PopulateContextContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ExtendedPopulateContextContainer extends PopulateContextContainer {

    @Autowired
    ContextContainer contextContainer;

    public ExtendedPopulateContextContainer() {
        super();
    }

    @Override
    protected void doPreProcessing(ChenileExchange exchange) {
        super.doPreProcessing(exchange);
        String tenantId = exchange.getHeader("x-tenant-id", String.class);
        String subTenantId = exchange.getHeader("x-sub-tenant-id", String.class);
        contextContainer.put("x-sub-tenant-id", subTenantId != null ? subTenantId : "default-subtenant");
        System.out.println("Tenant context initialized: tenant=" + tenantId + ", subTenant=" + subTenantId);
    }
}
