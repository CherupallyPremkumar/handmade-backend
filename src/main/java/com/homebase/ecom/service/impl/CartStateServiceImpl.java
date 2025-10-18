package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.Cart;
import com.homebase.ecom.entitystore.CartEntityStore;
import com.homebase.ecom.entitystore.impl.CartEntityStoreImpl;
import com.homebase.ecom.service.CartStateService;
import org.chenile.core.context.ContextContainer;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;


public class CartStateServiceImpl extends StateEntityServiceImpl<Cart> implements CartStateService<Cart> {


    CartEntityStoreImpl cartEntityStore;

    ContextContainer contextContainer;

    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     */
    public CartStateServiceImpl(STM<Cart> stm, STMActionsInfoProvider stmActionsInfoProvider,
                                EntityStore<Cart> entityStore,
                                CartEntityStore<Cart> cartEntityStore,
                                ContextContainer contextContainer
    ) {
        super(stm, stmActionsInfoProvider, entityStore);
        this.cartEntityStore= (CartEntityStoreImpl) cartEntityStore;
        this.contextContainer=contextContainer;
    }

    @Override
    protected Cart processEntity(Cart entity, String event, Object payload) {
        Cart cart=cartEntityStore.findByCustomerId(contextContainer.getUser());
        if(cart!=null){
            return cart;
        }
        entity.setCustomerId(contextContainer.getUser());
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
