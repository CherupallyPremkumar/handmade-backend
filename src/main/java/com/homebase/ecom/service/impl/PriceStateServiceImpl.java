package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.Price;
import com.homebase.ecom.service.PriceStateService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

public class PriceStateServiceImpl extends StateEntityServiceImpl<Price> implements PriceStateService {

    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     */
    public PriceStateServiceImpl(STM<Price> stm, STMActionsInfoProvider stmActionsInfoProvider, EntityStore<Price> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    protected Price processEntity(Price entity, String event, Object payload) {
        return super.processEntity(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Price> create(Price entity) {
        return super.create(entity);
    }

    @Override
    public StateEntityServiceResponse<Price> process(Price entity, String event, Object payload) {
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Price> processById(String id, String event, Object payload) {
        return super.processById(id, event, payload);
    }
}
