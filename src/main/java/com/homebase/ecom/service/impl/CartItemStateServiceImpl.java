package com.homebase.ecom.service.impl;

import com.homebase.ecom.command.cartitem.CreateCartItemRequest;
import com.homebase.ecom.domain.Cart;
import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.entity.CartEntity;
import com.homebase.ecom.service.CartItemStateService;
import com.homebase.ecom.service.CartStateService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class CartItemStateServiceImpl extends StateEntityServiceImpl<CartItem> implements CartItemStateService<CartItem> {




    protected final CartStateService cartStateService;

    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     */
    public CartItemStateServiceImpl(STM<CartItem> stm, STMActionsInfoProvider stmActionsInfoProvider, EntityStore<CartItem> entityStore, CartStateService cartStateService) {
        super(stm, stmActionsInfoProvider, entityStore);
        this.cartStateService = cartStateService;
    }

    @Override
    protected CartItem processEntity(CartItem entity, String event, Object payload) {
        return super.processEntity(entity, event, payload);
    }

    public StateEntityServiceResponse<CartItem> create(CartItem entity) {
        CartItem cartItem = new CartItem();
        cartItem.setProductVariantId(entity.getProductVariantId());
        cartItem.setCartId(entity.getCartId());
        StateEntityServiceResponse<CartItem> serviceResponse=super.create(entity);
        serviceResponse= processById(serviceResponse.getMutatedEntity().getId(), "addItem", null);
        cartStateService.refreshCart(serviceResponse.getMutatedEntity().getCartId());
        return serviceResponse;
    }


    @Override
    public StateEntityServiceResponse<CartItem> process(CartItem entity, String event, Object payload) {
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<CartItem> processById(String id, String event, Object payload) {
        StateEntityServiceResponse<CartItem> serviceResponse=  super.processById(id, event, payload);
        cartStateService.refreshCart(serviceResponse.getMutatedEntity().getCartId());
        return serviceResponse;
    }




}
