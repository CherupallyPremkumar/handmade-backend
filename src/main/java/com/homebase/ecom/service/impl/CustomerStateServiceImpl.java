package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.Cart;
import com.homebase.ecom.domain.Customer;
import com.homebase.ecom.service.CustomerStateService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;


public class CustomerStateServiceImpl extends StateEntityServiceImpl<Customer> implements CustomerStateService<Customer> {



    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     */
    public CustomerStateServiceImpl(STM<Customer> stm, STMActionsInfoProvider stmActionsInfoProvider, EntityStore<Customer> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    protected Customer processEntity(Customer entity, String event, Object payload) {
        return super.processEntity(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Customer> create(Customer entity) {
        return super.create(entity);
    }

    @Override
    public StateEntityServiceResponse<Customer> process(Customer entity, String event, Object payload) {
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Customer> processById(String id, String event, Object payload) {
        return super.processById(id, event, payload);
    }
}
