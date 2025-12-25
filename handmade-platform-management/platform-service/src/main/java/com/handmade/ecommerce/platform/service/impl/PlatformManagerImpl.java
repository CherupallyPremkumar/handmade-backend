package com.handmade.ecommerce.platform.service.impl;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.service.PlatformManager;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

/**
 * Platform Manager Implementation
 * Manages platform lifecycle and state transitions
 * Similar to ProcessManagerImpl in chenile-process-management
 */
public class PlatformManagerImpl extends StateEntityServiceImpl<PlatformOwner> implements PlatformManager {

    /**
     * Constructor
     * @param stm the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore the store for persisting the entity
     */
    public PlatformManagerImpl(STM<PlatformOwner> stm, 
                               STMActionsInfoProvider stmActionsInfoProvider, 
                               EntityStore<PlatformOwner> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    public StateEntityServiceResponse<PlatformOwner> create(PlatformOwner platform) {
        // Set default state if not set
        if (platform.getCurrentState() == null) {
            // Platform starts in BOOTSTRAPPING state
            platform.setCurrentState("BOOTSTRAPPING");
        }
        return super.create(platform);
    }

    @Override
    public StateEntityServiceResponse<PlatformOwner> retrieve(String id) {
        return super.retrieve(id);
    }

    @Override
    public StateEntityServiceResponse<PlatformOwner> update(String id, PlatformOwner platform) {
        return super.update(id, platform);
    }

    @Override
    public StateEntityServiceResponse<PlatformOwner> processById(String id, String eventId, Object payload) {
        // Process state transition events
        return super.processById(id, eventId, payload);
    }
}
