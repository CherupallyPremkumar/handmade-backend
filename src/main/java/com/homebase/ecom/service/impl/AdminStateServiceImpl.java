package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.Admin;
import com.homebase.ecom.service.AdminStateService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

public class AdminStateServiceImpl extends StateEntityServiceImpl<Admin> implements AdminStateService {

    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     */
    public AdminStateServiceImpl(STM<Admin> stm, STMActionsInfoProvider stmActionsInfoProvider, EntityStore<Admin> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    protected Admin processEntity(Admin entity, String event, Object payload) {
        return super.processEntity(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Admin> create(Admin entity) {
        return super.create(entity);
    }

    @Override
    public StateEntityServiceResponse<Admin> process(Admin entity, String event, Object payload) {
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Admin> processById(String id, String event, Object payload) {
        return super.processById(id, event, payload);
    }
}
