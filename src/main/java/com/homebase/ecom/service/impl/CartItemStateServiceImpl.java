package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.service.CartItemStateService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

public class CartItemStateServiceImpl extends StateEntityServiceImpl<CartItem> implements CartItemStateService {

    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     */
    public CartItemStateServiceImpl(STM<CartItem> stm, STMActionsInfoProvider stmActionsInfoProvider, EntityStore<CartItem> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    protected CartItem processEntity(CartItem entity, String event, Object payload) {
        return super.processEntity(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<CartItem> create(CartItem entity) {
        return super.create(entity);
    }

    @Override
    public StateEntityServiceResponse<CartItem> process(CartItem entity, String event, Object payload) {
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<CartItem> processById(String id, String event, Object payload) {
        return super.processById(id, event, payload);
    }
}
