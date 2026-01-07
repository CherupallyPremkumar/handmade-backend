package com.handmade.ecommerce.policy.service.impl;

import com.handmade.ecommerce.policy.api.ReturnPolicyManager;
import com.handmade.ecommerce.policy.domain.aggregate.ReturnPolicy;
import com.handmade.ecommerce.policy.domain.valueobject.ReturnDecision;
import com.handmade.ecommerce.policy.service.store.ReturnPolicyEntityStore;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

import java.time.LocalDate;

public class ReturnPolicyManagerImpl extends StateEntityServiceImpl<ReturnPolicy> implements ReturnPolicyManager {

    private ReturnPolicyEntityStore entityStore;

    public ReturnPolicyManagerImpl(STM<ReturnPolicy> stm, STMActionsInfoProvider infoProvider, 
            EntityStore<ReturnPolicy> entityStore) {
        super(stm, infoProvider, entityStore);
        this.entityStore = (ReturnPolicyEntityStore) entityStore;
    }

    @Override
    public ReturnPolicy resolveActivePolicy(String country, String category, LocalDate date) {
        return entityStore.findActivePolicy(country, category, date).orElse(null);
    }

    @Override
    public ReturnDecision validateReturnRequest(String country, String category, String orderId, String reason) {
        ReturnPolicy policy = resolveActivePolicy(country, category, LocalDate.now());
        if (policy == null) return ReturnDecision.PRE_APPROVED;
        return ReturnDecision.PRE_APPROVED;
    }
}
