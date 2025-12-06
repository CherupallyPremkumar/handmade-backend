package com.handmade.ecommerce.cartline.service.impl;

import com.handmade.ecommerce.cartline.model.Cartline;
import com.handmade.ecommerce.cartline.service.CartlineService;
import com.handmade.ecommerce.cartline.service.store.CartlineEntityStore;
import com.handmade.ecommerce.command.cart.AddCartLinePayload;
import com.handmade.ecommerce.command.cart.CartLineDecrementQtyPayLoad;
import com.handmade.ecommerce.command.cart.CartLineIncrementQtyPayLoad;
import com.handmade.ecommerce.command.cart.UpdateCartLinePayload;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.api.StateEntityService;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Base CartLine Service with common logic
 * Provides common methods for cartline operations
 * Subclasses should implement CartlineService interface
 */
public abstract class BaseCartLineServiceImpl extends StateEntityServiceImpl<Cartline> {

    private static final Logger logger = LoggerFactory.getLogger(BaseCartLineServiceImpl.class);
    
    @Autowired
    protected CartlineEntityStore cartLineEntityStore;

    public BaseCartLineServiceImpl(STM<Cartline> stm, 
                              STMActionsInfoProvider stmActionsInfoProvider, 
                              EntityStore<Cartline> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    protected Cartline getCartLine(String cartLineId) {
        return super.retrieve(cartLineId).getMutatedEntity();
    }

    protected List<Cartline> getCartLines(String cartId) {
        return cartLineEntityStore.getCartLines(cartId);
    }



    @Override
    public StateEntityServiceResponse<Cartline> create(Cartline entity) {
        Cartline cartline = checkVariantExists(entity.getCartId(),entity.getVariantId());
        if(cartline != null){
            cartline=updateQty(cartline);
            StateEntityServiceResponse<Cartline> stateEntityService=new StateEntityServiceResponse<>();
            stateEntityService.setMutatedEntity(cartline);
            return stateEntityService;
        }
        return super.create(entity);
    }

    protected Cartline checkVariantExists(String cartId,String variantId){
        return checkVariantWithCreated(cartId,variantId);
    }

    private Cartline checkVariantWithCreated(String cartId, String variantId) {
        return cartLineEntityStore.checkVariantExistsWithCreated(cartId,variantId,"CREATED");
    }


    protected Cartline updateQty(Cartline cartline) {
        return process(cartline, CartlineEvent.UPDATE_QTY, null).getMutatedEntity();
    }

    protected Cartline updateQty(String cartLineId, UpdateCartLinePayload payload) {
       return processById(cartLineId, CartlineEvent.UPDATE_QTY, null).getMutatedEntity();
    }

    protected Cartline incrementQty(String cartLineId, CartLineIncrementQtyPayLoad payLoad) {
        return processById(cartLineId, CartlineEvent.INCREMENT_QTY, payLoad).getMutatedEntity();
    }

    protected Cartline decrementQty(String cartLineId, CartLineDecrementQtyPayLoad payload) {
        return processById(cartLineId, CartlineEvent.DECREMENT_QTY, null).getMutatedEntity();
    }
    protected void close(String cartLineId,Object payload) {
        processById(cartLineId, CartlineEvent.CLOSE, null);
    }

    protected void changeCart(String cartLineId, String cartId) {
        processById(cartLineId, CartlineEvent.CHANGE_CART, cartId);
    }

    protected void changeCart(Cartline cartline, String cartId) {
        process(cartline, CartlineEvent.CHANGE_CART, cartId);
    }

    protected void delete(String cartLineId, Object payload) {
        processById(cartLineId, CartlineEvent.DELETE, payload);
    }

    protected void delete(Cartline cartline) {
        process(cartline, CartlineEvent.DELETE, null);
    }




}
