package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.Wishlist;
import com.homebase.ecom.service.WishlistStateService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

public class WishlistStateServiceImpl extends StateEntityServiceImpl<Wishlist> implements WishlistStateService {

    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     */
    public WishlistStateServiceImpl(STM<Wishlist> stm, STMActionsInfoProvider stmActionsInfoProvider, EntityStore<Wishlist> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    protected Wishlist processEntity(Wishlist entity, String event, Object payload) {
        return super.processEntity(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Wishlist> create(Wishlist entity) {
        return super.create(entity);
    }

    @Override
    public StateEntityServiceResponse<Wishlist> process(Wishlist entity, String event, Object payload) {
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Wishlist> processById(String id, String event, Object payload) {
        return super.processById(id, event, payload);
    }
}
