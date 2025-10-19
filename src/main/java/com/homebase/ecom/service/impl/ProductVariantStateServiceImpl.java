package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.ProductVariant;
import com.homebase.ecom.service.ProductVariantStateService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

public class ProductVariantStateServiceImpl extends StateEntityServiceImpl<ProductVariant> implements ProductVariantStateService {

    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     */
    public ProductVariantStateServiceImpl(STM<ProductVariant> stm, STMActionsInfoProvider stmActionsInfoProvider, EntityStore<ProductVariant> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    protected ProductVariant processEntity(ProductVariant entity, String event, Object payload) {
        return super.processEntity(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<ProductVariant> create(ProductVariant entity) {
        return super.create(entity);
    }

    @Override
    public StateEntityServiceResponse<ProductVariant> process(ProductVariant entity, String event, Object payload) {
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<ProductVariant> processById(String id, String event, Object payload) {
        return super.processById(id, event, payload);
    }
}
