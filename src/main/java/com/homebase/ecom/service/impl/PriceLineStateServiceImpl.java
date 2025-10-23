package com.homebase.ecom.service.impl;

import com.homebase.ecom.command.priceline.PriceLinePayload;
import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.Price;
import com.homebase.ecom.domain.PriceLine;
import com.homebase.ecom.entitystore.PriceLineEntityStore;
import com.homebase.ecom.service.PriceLineStateService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

import java.math.BigDecimal;

public class PriceLineStateServiceImpl extends StateEntityServiceImpl<PriceLine> implements PriceLineStateService,BasePriceLineCalculator {
    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     */


    public PriceLineStateServiceImpl(STM<PriceLine> stm, STMActionsInfoProvider stmActionsInfoProvider, EntityStore<PriceLine> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    public StateEntityServiceResponse<PriceLine> create(PriceLine entity) {
        return super.create(entity);
    }

    @Override
    public StateEntityServiceResponse<PriceLine> process(PriceLine entity, String event, Object payload) {
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<PriceLine> processById(String id, String event, Object payload) {
        return super.processById(id, event, payload);
    }


    @Override
    public CartItem calculateBasePrice(PriceLine priceLine, CartItem cartItem) {
        if (cartItem == null) return null;
        if (priceLine == null) throw new IllegalStateException("Price not found for productVariantId: " + cartItem.getProductVariantId());
        BigDecimal basePrice = priceLine.getAmount() != null ? priceLine.getAmount() : BigDecimal.ZERO;
        cartItem.setOriginalPrice(basePrice);
        cartItem.setSnapshotPrice(cartItem.getSnapshotPrice() == null ? basePrice : cartItem.getSnapshotPrice());
        cartItem.setSalePrice(priceLine.getFinalPrice() != null ? priceLine.getFinalPrice() : basePrice);
        cartItem.setWasOnSale(priceLine.isOnSale());

        return cartItem;
    }

    @Override
    public PriceLine createPriceLine(PriceLinePayload payload) {
        if (payload == null) {
            throw new IllegalArgumentException("PriceLinePayload cannot be null");
        }
        PriceLine priceLine = new PriceLine();
        priceLine.setPriceId(payload.getPriceId());
        priceLine.setProductVariantId(payload.getProductVariantId());
        priceLine.setAmount(payload.getAmount() != null ? payload.getAmount() : BigDecimal.ZERO);
        priceLine.setSaleAmount(payload.getSaleAmount());
        priceLine.setDiscountPercentage(payload.getDiscountPercentage());
        priceLine.setCurrency(payload.getCurrency());
        priceLine.setRegion(payload.getRegion());
        priceLine.setPriceType(payload.getPriceType());
        priceLine.setSaleStartDate(payload.getSaleStartDate());
        priceLine.setSaleEndDate(payload.getSaleEndDate());
        priceLine.setTaxCategory(payload.getTaxCategory());
        StateEntityServiceResponse<PriceLine> response = this.create(priceLine);
        return response.getMutatedEntity();
    }
}

