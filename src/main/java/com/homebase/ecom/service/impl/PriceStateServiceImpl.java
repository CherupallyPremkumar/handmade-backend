package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.Price;
import com.homebase.ecom.entitystore.PriceEntityStore;
import com.homebase.ecom.service.PriceStateService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

import java.math.BigDecimal;

public class PriceStateServiceImpl extends StateEntityServiceImpl<Price> implements PriceStateService {



    PriceEntityStore priceEntityStore;
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

        // 1️⃣ Fetch price info from the price store
        Price price = priceEntityStore.findPriceByProductVariantId(cartItem.getProductVariantId());
        if (price == null) {
            throw new IllegalStateException("Price not found for productVariantId: " + cartItem.getProductVariantId());
        }

        // 2️⃣ Set original/base price
        BigDecimal basePrice = price.getAmount() != null ? price.getAmount() : BigDecimal.ZERO;
        cartItem.setOriginalPrice(basePrice);

        // 3️⃣ Determine the current (possibly discounted) sale price
        BigDecimal finalPriceValue = price.getFinalPrice();
        cartItem.setSalePrice(finalPriceValue != null ? finalPriceValue : basePrice);

        // 4️⃣ Set snapshot price only the first time item is added
        if (cartItem.getSnapshotPrice() == null) {
            cartItem.setSnapshotPrice(basePrice);
        }

        // 5️⃣ Track if item *was* on sale during pricing
        cartItem.setWasOnSale(price.isOnSale());

        // 6️⃣ Calculate total = salePrice × quantity
        int quantity = (cartItem.getQuantity() != null) ? cartItem.getQuantity() : 0;
        BigDecimal totalAmount = cartItem.getSalePrice().multiply(BigDecimal.valueOf(quantity));
        cartItem.setTotalAmount(totalAmount);

        return cartItem;
    }
}
