package com.handmade.ecommerce.policy.service.impl;

import com.handmade.ecommerce.policy.api.InventoryPolicyManager;
import com.handmade.ecommerce.policy.domain.aggregate.InventoryPolicy;
import com.handmade.ecommerce.policy.domain.valueobject.InventoryDecision;
import com.handmade.ecommerce.policy.service.store.InventoryPolicyEntityStore;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

import java.time.LocalDate;

public class InventoryPolicyManagerImpl extends StateEntityServiceImpl<InventoryPolicy> implements InventoryPolicyManager {

    private InventoryPolicyEntityStore entityStore;

    public InventoryPolicyManagerImpl(STM<InventoryPolicy> stm, STMActionsInfoProvider infoProvider, 
            EntityStore<InventoryPolicy> entityStore) {
        super(stm, infoProvider, entityStore);
        this.entityStore = (InventoryPolicyEntityStore) entityStore;
    }

    @Override
    public InventoryPolicy resolveActivePolicy(String country, String category, LocalDate date) {
        return entityStore.findActivePolicy(country, category, date).orElse(null);
    }

    @Override
    public InventoryDecision validateStockUpdate(String country, String category, String productId, Integer quantity) {
        InventoryPolicy policy = resolveActivePolicy(country, category, LocalDate.now());
        if (policy == null) return InventoryDecision.ALLOW;
        // Business logic for stock validation based on policy thresholds would go here
        return InventoryDecision.ALLOW;
    }
}
