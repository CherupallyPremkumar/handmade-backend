package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.Cart;
import com.homebase.ecom.service.CartStateService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

public class CartStateServiceImpl extends StateEntityServiceImpl<Cart> implements CartStateService {

    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     */
    public CartStateServiceImpl(STM<Cart> stm, STMActionsInfoProvider stmActionsInfoProvider, EntityStore<Cart> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    protected Cart processEntity(Cart entity, String event, Object payload) {
        return super.processEntity(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Cart> create(Cart entity) {
        return super.create(entity);
    }

    @Override
    public StateEntityServiceResponse<Cart> process(Cart entity, String event, Object payload) {
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Cart> processById(String id, String event, Object payload) {
        return super.processById(id, event, payload);
    }
}
