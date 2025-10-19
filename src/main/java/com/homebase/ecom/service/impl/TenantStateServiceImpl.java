package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.Tenant;
import com.homebase.ecom.service.TenantStateService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

public class TenantStateServiceImpl extends StateEntityServiceImpl<Tenant> implements TenantStateService {

    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     */
    public TenantStateServiceImpl(STM<Tenant> stm, STMActionsInfoProvider stmActionsInfoProvider, EntityStore<Tenant> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    protected Tenant processEntity(Tenant entity, String event, Object payload) {
        return super.processEntity(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Tenant> create(Tenant entity) {
        return super.create(entity);
    }

    @Override
    public StateEntityServiceResponse<Tenant> process(Tenant entity, String event, Object payload) {
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Tenant> processById(String id, String event, Object payload) {
        return super.processById(id, event, payload);
    }
}
