package com.handmade.ecommerce.cartline.service.store;

import org.chenile.stm.State;
import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.cartline.model.Cartline;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.cartline.configuration.dao.CartlineRepository;

import java.util.List;
import java.util.Optional;

public class CartlineEntityStore implements EntityStore<Cartline>{
    @Autowired private CartlineRepository cartlineRepository;

	@Override
	public void store(Cartline entity) {
        cartlineRepository.save(entity);
	}

	@Override
	public Cartline retrieve(String id) {
        Optional<Cartline> entity = cartlineRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Cartline with ID " + id);
	}

    public List<Cartline> getCartLines(String cartId) {
        return cartlineRepository.findByCartId(cartId);
    }

    public Cartline checkVariantExists(String cartId, String variantId) {
        return null;
    }

    public Cartline checkVariantExistsWithCreated(String cartId, String variantId , State state) {
       Optional<Cartline> cartline=cartlineRepository.findByCartIdAndVariantIdAndState(cartId, variantId,state);
       return cartline.orElse(null);
    }
}
