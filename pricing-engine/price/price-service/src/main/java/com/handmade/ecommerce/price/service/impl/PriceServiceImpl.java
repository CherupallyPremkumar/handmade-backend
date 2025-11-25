//package com.handmade.ecommerce.price.service.impl;
//
//
//import com.handmade.ecommerce.price.PriceService;
//import com.handmade.ecommerce.price.model.Price;
//import com.handmade.ecommerce.price.model.PriceLine;
//import com.handmade.ecommerce.price.service.store.PriceEntityStore;
//import org.chenile.base.exception.BadRequestException;
//import org.chenile.base.exception.ServerException;
//import org.chenile.stm.STM;
//import org.chenile.stm.impl.STMActionsInfoProvider;
//import org.chenile.workflow.dto.StateEntityServiceResponse;
//import org.chenile.workflow.service.impl.StateEntityServiceImpl;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.Objects;
//
//import static org.chenile.core.context.ContextContainer.CONTEXT_CONTAINER;
//
///**
// * State service implementation for Price with improved error handling and
// * logging
// */
//public class PriceServiceImpl extends StateEntityServiceImpl<Price> implements PriceService {
//
//    private static final Logger logger = LoggerFactory.getLogger(PriceStateServiceImpl.class);
//
//    private final PriceEntityStore priceEntityStore;
//
//    private final PriceLineServiceFactory priceLineServiceFactory;
//
//    /**
//     * @param stm                    the state machine that has read the
//     *                               corresponding State Transition Diagram
//     * @param stmActionsInfoProvider the provider that gives out info about the
//     *                               state diagram
//     * @param entityStore            the store for persisting the entity
//     */
//    public PriceServiceImpl(STM<Price> stm, STMActionsInfoProvider stmActionsInfoProvider,
//                                 PriceEntityStore entityStore,PriceLineServiceFactory priceLineServiceFactory) {
//        super(stm, stmActionsInfoProvider, entityStore);
//        priceEntityStore = entityStore;
//        this.priceLineServiceFactory=priceLineServiceFactory;
//    }
//
//    @Override
//    protected Price processEntity(Price entity, String event, Object payload) {
//        return super.processEntity(entity, event, payload);
//    }
//
//    @Override
//    public StateEntityServiceResponse<Price> create(Price entity) {
//        return super.create(entity);
//    }
//
//    @Override
//    public StateEntityServiceResponse<Price> processById(String id, String event, Object payload) {
//        Price price = new Price();
//        if (Objects.equals(id, "0")) {
//            makePriceObject(price, (PriceRequest) payload);
//            super.create(price);
//        }
//        PriceLine priceLine = createPriceLine(price.getId(), (PriceRequest) payload);
//        if (priceLine == null) {
//            throw new ServerException(ErrorCodes.PRICE_NOT_FOUND.getSubError(), new Object[] { priceLine });
//        }
//        return super.processById(price.getId(), null, null);
//    }
//
//    private void makePriceObject(Price price, PriceRequest payload) {
//        price.setSubTenantId(HmHeaderUtils.getSubTenantIdKey(CONTEXT_CONTAINER.toMap()));
//        price.setProductVariantId(payload.getProductVariantId());
//    }
//
//    private PriceLine createPriceLine(String priceId, PriceRequest priceLinePayload) {
//        PriceLineStrategyService priceLineService = priceLineServiceFactory.getFactory(priceLinePayload.getCurrency());
//        try {
//            return priceLineService.create(priceLinePayload);
//        } catch (BadRequestException e) {
//            throw new BadRequestException(e.getMessage(), e);
//        } catch (Exception e) {
//            throw new BadRequestException("An unexpected error occurred during price processing", e);
//        }
//    }
//
//    @Override
//    public CartItem calculate(CartItem cartItem) {
//        if (cartItem == null)
//            return null;
//        Price price = priceEntityStore.findPriceByProductVariantId(cartItem.getProductVariantId());
//        if (price == null) {
//            throw new IllegalStateException("Price not found for productVariantId: " + cartItem.getProductVariantId());
//        }
//        PriceLineStrategyService strategy = priceLineServiceFactory.getFactory(cartItem.getCountry());
//        cartItem = strategy.calculatePrice(price.getId(), cartItem);
//        return cartItem;
//    }
//
//    @Override
//    public void createPrice(String variantId, List<PriceRequest> priceRequests) {
//        priceRequests.stream().forEach(p->{
//            p.setProductVariantId(variantId);
//            processById("0","addPriceLine",priceRequests);
//        });
//
//    }
//
//}
