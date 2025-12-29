package com.handmade.ecommerce.platform.service.impl;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.api.PlatformManager;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;


public class PlatformManagerImpl extends StateEntityServiceImpl<PlatformOwner> implements PlatformManager {

    public PlatformManagerImpl(STM<PlatformOwner> stm, 
                               STMActionsInfoProvider stmActionsInfoProvider, 
                               EntityStore<PlatformOwner> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }
    @Override
    public StateEntityServiceResponse<PlatformOwner> create(PlatformOwner platform) {
        // Framework's GenericEntryAction will call entityStore.store() automatically
        return super.create(platform);
    }

    @Override
    public StateEntityServiceResponse<PlatformOwner> processById(String id, String eventId, Object payload) {
        // Process state transition events
        return super.processById(id, eventId, payload);
    }
}
