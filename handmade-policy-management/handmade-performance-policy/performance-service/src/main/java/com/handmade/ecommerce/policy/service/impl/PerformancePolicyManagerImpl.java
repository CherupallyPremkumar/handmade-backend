package com.handmade.ecommerce.policy.service.impl;

import com.handmade.ecommerce.policy.api.PerformancePolicyManager;
import com.handmade.ecommerce.policy.domain.aggregate.PerformancePolicy;
import com.handmade.ecommerce.policy.domain.valueobject.PerformanceDecision;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

public class PerformancePolicyManagerImpl extends StateEntityServiceImpl<PerformancePolicy> implements PerformancePolicyManager {

    public PerformancePolicyManagerImpl(STM<PerformancePolicy> stm, STMActionsInfoProvider infoProvider, 
            EntityStore<PerformancePolicy> entityStore) {
        super(stm, infoProvider, entityStore);
    }

    @Override
    public PerformanceDecision evaluateSellerPerformance(String sellerId) {
        return PerformanceDecision.GOOD;
    }
}
