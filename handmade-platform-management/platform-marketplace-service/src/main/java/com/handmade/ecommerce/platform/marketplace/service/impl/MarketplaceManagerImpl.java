package com.handmade.ecommerce.platform.marketplace.service;

import com.handmade.ecommerce.platform.api.MarketplaceManager;
import com.handmade.ecommerce.platform.domain.aggregate.Marketplace;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

/**
 * Marketplace Manager Implementation
 * Uses Chenile State Machine for lifecycle management
 * 
 * State Flow: DRAFT → ACTIVE → SUSPENDED
 * 
 * Follows same pattern as PlatformManagerImpl
 */
public class MarketplaceManagerImpl extends StateEntityServiceImpl<Marketplace> 
                                     implements MarketplaceManager {
    
    public MarketplaceManagerImpl(STM<Marketplace> stm,
                                 STMActionsInfoProvider stmActionsInfoProvider,
                                 EntityStore<Marketplace> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }
    
    @Override
    public StateEntityServiceResponse<Marketplace> create(Marketplace marketplace) {
        // Framework's GenericEntryAction will call entityStore.store() automatically
        return super.create(marketplace);
    }
    
    @Override
    public StateEntityServiceResponse<Marketplace> processById(String id, String eventId, Object payload) {
        // Process state transition events
        return super.processById(id, eventId, payload);
    }
}
