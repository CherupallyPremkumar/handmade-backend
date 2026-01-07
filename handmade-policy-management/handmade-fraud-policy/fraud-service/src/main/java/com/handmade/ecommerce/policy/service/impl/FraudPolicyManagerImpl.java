package com.handmade.ecommerce.policy.service.impl;

import com.handmade.ecommerce.policy.api.FraudPolicyManager;
import com.handmade.ecommerce.policy.domain.aggregate.FraudPolicy;
import com.handmade.ecommerce.policy.domain.valueobject.FraudDecision;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

public class FraudPolicyManagerImpl extends StateEntityServiceImpl<FraudPolicy> implements FraudPolicyManager {

    public FraudPolicyManagerImpl(STM<FraudPolicy> stm, STMActionsInfoProvider infoProvider, 
            EntityStore<FraudPolicy> entityStore) {
        super(stm, infoProvider, entityStore);
    }

    @Override
    public FraudDecision evaluateTransaction(String userId, Object transactionData) {
        // Basic placeholder logic
        return FraudDecision.TRUSTED;
    }
}
