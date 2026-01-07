package com.handmade.ecommerce.commission.service.impl;

import com.handmade.ecommerce.commission.api.CommissionPolicyManager;
import com.handmade.ecommerce.commission.domain.aggregate.CommissionPolicy;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

/**
 * Implementation of CommissionPolicyManager
 * Coordinates between STM and EntityStore
 */
public class CommissionPolicyManagerImpl extends StateEntityServiceImpl<CommissionPolicy> implements CommissionPolicyManager {

    public CommissionPolicyManagerImpl(STM<CommissionPolicy> stm, 
                                     STMActionsInfoProvider infoProvider,
                                     EntityStore<CommissionPolicy> entityStore) {
        super(stm, infoProvider, entityStore);
    }
}
