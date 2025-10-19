package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.Category;
import com.homebase.ecom.service.CategoryStateService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

public class CategoryStateServiceImpl extends StateEntityServiceImpl<Category> implements CategoryStateService {

    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     */
    public CategoryStateServiceImpl(STM<Category> stm, STMActionsInfoProvider stmActionsInfoProvider, EntityStore<Category> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    protected Category processEntity(Category entity, String event, Object payload) {
        return super.processEntity(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Category> create(Category entity) {
        return super.create(entity);
    }

    @Override
    public StateEntityServiceResponse<Category> process(Category entity, String event, Object payload) {
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Category> processById(String id, String event, Object payload) {
        return super.processById(id, event, payload);
    }
}
