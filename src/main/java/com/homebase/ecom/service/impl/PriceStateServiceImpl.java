package com.homebase.ecom.service.impl;

import com.homebase.ecom.command.priceline.PriceLinePayload;
import com.homebase.ecom.constants.HmHeaderUtils;
import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.Price;
import com.homebase.ecom.domain.PriceLine;
import com.homebase.ecom.entitystore.PriceEntityStore;
import com.homebase.ecom.errorcodes.ErrorCodes;
import com.homebase.ecom.service.PriceStateService;
import com.homebase.ecom.service.price.factory.PriceLineServiceFactory;
import com.homebase.ecom.service.price.factory.PriceLineStrategyService;

import org.chenile.base.exception.BadRequestException;
import org.chenile.base.exception.ServerException;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

import java.math.BigDecimal;
import java.util.Objects;

import static org.chenile.core.context.ContextContainer.CONTEXT_CONTAINER;

public class PriceStateServiceImpl extends StateEntityServiceImpl<Price> implements PriceStateService {

    PriceEntityStore priceEntityStore;
    PriceLineServiceFactory priceLineServiceFactory;
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
    public StateEntityServiceResponse<Price> processById(String id, String event, Object payload) {
        Price price = new Price();
        if (Objects.equals(id, "0")) {
            makePriceObject(price, (PriceLinePayload) payload);
            super.create(price);
        }
        PriceLine priceLine=createPriceLine(price.getId(), (PriceLinePayload) payload);
        if (priceLine == null) {
            throw new ServerException(ErrorCodes.PRICE_NOT_FOUND.getSubError(), new Object[]{priceLine});
        }
        return super.processById(price.getId(), null,null);
    }

    private void makePriceObject(Price price, PriceLinePayload payload) {
        price.setSubTenantId(HmHeaderUtils.getSubTenantIdKey(CONTEXT_CONTAINER.toMap()));
        price.setProductVariantId(payload.getProductVariantId());
    }

    private PriceLine createPriceLine(String priceId, PriceLinePayload priceLinePayload) {
        PriceLineStrategyService priceLineService = priceLineServiceFactory.getFactory(priceLinePayload.getCurrency());
        try {
            return priceLineService.create(priceLinePayload);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage(), e);
        } catch (Exception e) {
            throw new BadRequestException("An unexpected error occurred during price processing", e);
        }
    }


    @Override
    public CartItem calculate(CartItem cartItem) {
        if (cartItem == null) return null;
        Price price = priceEntityStore.findPriceByProductVariantId(cartItem.getProductVariantId());
        if (price == null) {
            throw new IllegalStateException("Price not found for productVariantId: " + cartItem.getProductVariantId());
        }
        PriceLineStrategyService strategy = priceLineServiceFactory.getFactory(cartItem.getCountry());
        cartItem = strategy.calculatePrice(price.getId(),cartItem);
        return cartItem;
    }



}
