package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.Order;
import com.homebase.ecom.service.OrderStateService;
import org.chenile.stm.STM;
import org.chenile.stm.StateEntity;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

public class OrderStateServiceImpl extends StateEntityServiceImpl<Order> implements OrderStateService {


    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     */
    public OrderStateServiceImpl(STM<Order> stm, STMActionsInfoProvider stmActionsInfoProvider, EntityStore<Order> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    protected Order processEntity(Order entity, String event, Object payload) {
        return super.processEntity(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Order> create(Order entity) {
        return super.create(entity);
    }

    @Override
    public StateEntityServiceResponse<Order> process(Order entity, String event, Object payload) {
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Order> processById(String id, String event, Object payload) {
        return super.processById(id, event, payload);
    }


}
