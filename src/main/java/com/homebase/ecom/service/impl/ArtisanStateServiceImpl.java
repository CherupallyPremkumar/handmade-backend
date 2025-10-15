package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.Artisan;
import com.homebase.ecom.service.ArtisanStateService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

public class ArtisanStateServiceImpl extends StateEntityServiceImpl<Artisan> implements ArtisanStateService {
    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     */
    public ArtisanStateServiceImpl(STM<Artisan> stm, STMActionsInfoProvider stmActionsInfoProvider, EntityStore<Artisan> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    protected Artisan processEntity(Artisan entity, String event, Object payload) {
        return super.processEntity(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Artisan> create(Artisan entity) {
        return super.create(entity);
    }

    @Override
    public StateEntityServiceResponse<Artisan> process(Artisan entity, String event, Object payload) {
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Artisan> processById(String id, String event, Object payload) {
        return super.processById(id, event, payload);
    }
}
