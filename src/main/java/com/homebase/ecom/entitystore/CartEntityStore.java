package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Cart;
import com.homebase.ecom.entity.CartEntity;
import com.homebase.ecom.repository.CartRepository;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.stereotype.Component;

/**
 * Entity Store for Cart domain model.
 * Handles conversion between Cart domain model and CartEntity JPA entity.
 */

public interface CartEntityStore<T> {


}
