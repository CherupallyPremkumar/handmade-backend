package com.handmade.ecommerce.cartline.service.impl;

import com.handmade.ecommerce.cartline.model.Cartline;
import com.handmade.ecommerce.cartline.service.CartlineService;
import com.handmade.ecommerce.command.cart.AddCartLinePayload;
import com.handmade.ecommerce.command.cart.CartLineDecrementQtyPayLoad;
import com.handmade.ecommerce.command.cart.CartLineIncrementQtyPayLoad;
import com.handmade.ecommerce.command.cart.UpdateCartLinePayload;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Concrete CartLine Service Implementation
 * Extends base class and implements CartlineService interface
 */
@Service
public class CartLineServiceImpl extends BaseCartLineServiceImpl implements CartlineService {

    private static final Logger logger = LoggerFactory.getLogger(CartLineServiceImpl.class);

    public CartLineServiceImpl(STM<Cartline> stm,
                              STMActionsInfoProvider stmActionsInfoProvider,
                              EntityStore<Cartline> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    public void createCartLine(AddCartLinePayload payload) {
        Cartline cartline = new Cartline();
        cartline.setCartId(payload.getCartId());
        cartline.setVariantId(payload.getVariantId());
        cartline.setQuantity(payload.getQuantity());
        create(cartline);
    }

    @Override
    public void decrementCartLine(CartLineDecrementQtyPayLoad payLoad) {
        decrementQty(payLoad.getCartLineId(),payLoad);
    }

    @Override
    public void incrementCartLine(CartLineIncrementQtyPayLoad payLoad) {
        incrementQty(payLoad.getCartLineId(),payLoad);
    }

    @Override
    public void updateCartLine(UpdateCartLinePayload payload) {
        updateQty(payload.getCartLineId(),payload);
    }

    @Override
    public List<Cartline> getCartLines(String cartId) {
        return super.getCartLines(cartId);
    }

    @Override
    public Cartline getCartLine(String cartLineId) {
        return super.getCartLine(cartLineId);
    }

    @Override
    public void deleteCartItem(String cartLineId) {
        super.delete(cartLineId, null);
    }

    @Override
    public void deleteCartLines(String cartId) {
        logger.info("Deleting all cartlines for cart: cartId={}", cartId);
        List<Cartline> cartLines = getCartLines(cartId);
        for (Cartline cartLine : cartLines) {
            delete(cartLine);
        }
        logger.info("Deleted {} cartlines", cartLines.size());
    }

    @Override
    public void mergeCart(String oldCartId, String newCartId) {
        logger.info("Merging cartlines: oldCartId={}, newCartId={}", oldCartId, newCartId);
        List<Cartline> cartLines = getCartLines(oldCartId);
        for (Cartline cartLine : cartLines) {
            changeCart(cartLine, newCartId);
        }
        logger.info("Merged {} cartlines", cartLines.size());
    }
}
