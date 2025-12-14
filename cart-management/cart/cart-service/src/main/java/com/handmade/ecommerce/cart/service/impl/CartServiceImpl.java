package com.handmade.ecommerce.cart.service.impl;

import com.handmade.ecommerce.cart.configuration.dao.CartDao;
import com.handmade.ecommerce.cart.model.Cart;
import com.handmade.ecommerce.cart.service.CartService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Cart service implementation (Amazon-style architecture)
 * 
 * Key principles:
 * - No JPA relationships between Cart and Cartline
 * - String foreign keys (cartId)
 * - Separate queries for cart and items
 * - Simple CRUD operations
 * - No complex orchestration
 */
@Service
public class CartServiceImpl extends StateEntityServiceImpl<Cart> implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private CartDao cartDao;

    public CartServiceImpl(STM<Cart> stm, STMActionsInfoProvider stmActionsInfoProvider, EntityStore<Cart> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    public StateEntityServiceResponse<Cart> create(Cart entity) {
        // Try to find existing cart by sessionId (for guests) or userId (for logged-in users)
        Cart existingCart = null;

        if (entity.getSessionId() != null) {
            existingCart = cartDao.findBySessionId(entity.getSessionId()).orElse(null);
        } else if (entity.getUserId() != null) {
            existingCart = cartDao.findByUserId(entity.getUserId()).orElse(null);
        }

        if (existingCart != null) {
            logger.info("Found existing cart: {}", existingCart.getId());
            StateEntityServiceResponse<Cart> stateEntityServiceResponse=new StateEntityServiceResponse<>();
            stateEntityServiceResponse.setMutatedEntity(existingCart);
            return stateEntityServiceResponse;
        }

        // No existing cart - create new one through state machine
        logger.info("Creating new cart for sessionId: {} or userId: {}",
                entity.getSessionId(), entity.getUserId());
        return super.create(entity);
    }

}
