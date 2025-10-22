package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.Price;
import com.homebase.ecom.entitystore.PriceEntityStore;
import com.homebase.ecom.service.PriceStateService;
import com.homebase.ecom.service.price.factory.PriceServiceFactory;
import com.homebase.ecom.service.price.factory.PriceStrategyService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

import java.math.BigDecimal;

public class PriceStateServiceImpl extends StateEntityServiceImpl<Price> implements PriceStateService,BasePriceCalculator {



    PriceEntityStore priceEntityStore;

    PriceServiceFactory priceServiceFactory;
    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     */
    public PriceStateServiceImpl(STM<Price> stm, STMActionsInfoProvider stmActionsInfoProvider, PriceEntityStore entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
        priceEntityStore=entityStore;
    }

    @Override
    protected Price processEntity(Price entity, String event, Object payload) {
        return super.processEntity(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Price> create(Price entity) {
        return super.create(entity);
    }

    @Override
    public StateEntityServiceResponse<Price> process(Price entity, String event, Object payload) {
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Price> processById(String id, String event, Object payload) {
        return super.processById(id, event, payload);
    }

    @Override
    public Price createPrice(Price priceRequest) {
        return null;
    }

    @Override
    public Price processByPriceId(String id, String event, Object payload) {
        return null;
    }

    @Override
    public Price retrieveByPriceId(String id) {
        return null;
    }

    @Override
    public CartItem calculate(CartItem cartItem) {
        if (cartItem == null) return null;
        Price price = priceEntityStore.findPriceByProductVariantId(cartItem.getProductVariantId());
        if (price == null) {
            throw new IllegalStateException("Price not found for productVariantId: " + cartItem.getProductVariantId());
        }
        PriceStrategyService strategy = priceServiceFactory.getFactory(price);
        cartItem = strategy.calculatePrice(price,cartItem);
        return cartItem;
    }


    public CartItem calculateBasePrice(Price price,CartItem cartItem) {
        if (cartItem == null) return null;
        if (price == null) throw new IllegalStateException("Price not found for productVariantId: " + cartItem.getProductVariantId());

        BigDecimal basePrice = price.getAmount() != null ? price.getAmount() : BigDecimal.ZERO;
        cartItem.setOriginalPrice(basePrice);
        cartItem.setSnapshotPrice(cartItem.getSnapshotPrice() == null ? basePrice : cartItem.getSnapshotPrice());
        cartItem.setSalePrice(price.getFinalPrice() != null ? price.getFinalPrice() : basePrice);
        cartItem.setWasOnSale(price.isOnSale());

        return cartItem;
    }
}
