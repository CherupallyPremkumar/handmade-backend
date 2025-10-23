package com.homebase.ecom.service.impl;

import com.homebase.ecom.command.priceline.PriceLinePayload;
import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.PriceLine;
import com.homebase.ecom.entitystore.PriceLineEntityStore;
import com.homebase.ecom.service.PriceLineStateService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * State service implementation for PriceLine with improved price calculation
 * using PriceCalculationService for business logic
 */
public class PriceLineStateServiceImpl extends StateEntityServiceImpl<PriceLine> 
    implements PriceLineStateService, BasePriceLineCalculator {
    
    private static final Logger logger = LoggerFactory.getLogger(PriceLineStateServiceImpl.class);
    
    private final PriceCalculationService priceCalculationService;

    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     */
    public PriceLineStateServiceImpl(STM<PriceLine> stm, STMActionsInfoProvider stmActionsInfoProvider, EntityStore<PriceLine> entityStore, PriceCalculationService priceCalculationService) {
        super(stm, stmActionsInfoProvider, entityStore);
        this.priceCalculationService = priceCalculationService;
    }

    @Override
    public StateEntityServiceResponse<PriceLine> create(PriceLine entity) {
        logger.debug("Creating PriceLine for priceId: {}, currency: {}", 
            entity.getPriceId(), entity.getCurrency());
        return super.create(entity);
    }

    @Override
    public StateEntityServiceResponse<PriceLine> process(PriceLine entity, String event, Object payload) {
        logger.debug("Processing PriceLine {} with event: {}", entity.getId(), event);
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<PriceLine> processById(String id, String event, Object payload) {
        logger.debug("Processing PriceLine by ID: {} with event: {}", id, event);
        return super.processById(id, event, payload);
    }

    /**
     * Calculate base price for cart item using PriceCalculationService
     * @deprecated Use PriceCalculationService directly for better separation of concerns
     */
    @Override
    public CartItem calculateBasePrice(PriceLine priceLine, CartItem cartItem) {
        if (cartItem == null) {
            logger.warn("CartItem is null, cannot calculate price");
            return null;
        }
        if (priceLine == null) {
            String errorMsg = "Price not found for productVariantId: " + cartItem.getProductVariantId();
            logger.error(errorMsg);
            throw new IllegalStateException(errorMsg);
        }
        logger.debug("Calculating base price for productVariantId: {}", cartItem.getProductVariantId());
        
        // Use PriceCalculationService instead of deprecated domain methods
        BigDecimal basePrice = priceLine.getAmount() != null ? priceLine.getAmount() : BigDecimal.ZERO;
        BigDecimal finalPrice = priceCalculationService.calculateFinalPrice(priceLine);
        boolean isOnSale = priceCalculationService.isOnSale(priceLine);
        cartItem.setOriginalPrice(basePrice);
        cartItem.setSnapshotPrice(cartItem.getSnapshotPrice() == null ? basePrice : cartItem.getSnapshotPrice());
        cartItem.setSalePrice(finalPrice);
        cartItem.setWasOnSale(isOnSale);
        if (isOnSale) {
            BigDecimal savings = priceCalculationService.calculateSavings(priceLine);
            cartItem.setSavingAmount(savings);
        }
        logger.debug("Calculated prices - Base: {}, Final: {}, OnSale: {}", 
            basePrice, finalPrice, isOnSale);
        return cartItem;
    }

    @Override
    public PriceLine createPriceLine(PriceLinePayload payload) {
        if (payload == null) {
            throw new IllegalArgumentException("PriceLinePayload cannot be null");
        }
        logger.info("Creating PriceLine for priceId: {}, currency: {}, region: {}", 
            payload.getPriceId(), payload.getCurrency(), payload.getRegion());
        validatePriceLinePayload(payload);
        PriceLine priceLine = getPriceLine(payload);
        StateEntityServiceResponse<PriceLine> response = this.create(priceLine);
        logger.info("PriceLine created successfully with ID: {}", response.getMutatedEntity().getId());
        return response.getMutatedEntity();
    }

    private static PriceLine getPriceLine(PriceLinePayload payload) {
        PriceLine priceLine = new PriceLine();
        priceLine.setPriceId(payload.getPriceId());
        priceLine.setAmount(payload.getAmount() != null ? payload.getAmount() : BigDecimal.ZERO);
        priceLine.setSaleAmount(payload.getSaleAmount());
        priceLine.setDiscountPercentage(payload.getDiscountPercentage());
        priceLine.setCurrency(payload.getCurrency());
        priceLine.setRegion(payload.getRegion());
        priceLine.setPriceType(payload.getPriceType());
        priceLine.setSaleStartDate(payload.getSaleStartDate());
        priceLine.setSaleEndDate(payload.getSaleEndDate());
        priceLine.setTaxCategory(payload.getTaxCategory());
        return priceLine;
    }

    /**
     * Validate PriceLinePayload before creating
     */
    private void validatePriceLinePayload(PriceLinePayload payload) {
        if (payload.getPriceId() == null || payload.getPriceId().isBlank()) {
            throw new IllegalArgumentException("PriceId is required");
        }
        if (payload.getCurrency() == null || payload.getCurrency().isBlank()) {
            throw new IllegalArgumentException("Currency is required");
        }
        if (payload.getAmount() == null || payload.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (payload.getSaleAmount() != null && 
            payload.getSaleAmount().compareTo(payload.getAmount()) >= 0) {
            throw new IllegalArgumentException("Sale amount must be less than base amount");
        }
        if (payload.getDiscountPercentage() != null) {
            if (payload.getDiscountPercentage().compareTo(BigDecimal.ZERO) < 0 ||
                payload.getDiscountPercentage().compareTo(BigDecimal.valueOf(100)) > 0) {
                throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
            }
        }
        if (payload.getSaleStartDate() != null && payload.getSaleEndDate() != null) {
            if (payload.getSaleEndDate().isBefore(payload.getSaleStartDate())) {
                throw new IllegalArgumentException("Sale end date must be after start date");
            }
        }
    }
}
