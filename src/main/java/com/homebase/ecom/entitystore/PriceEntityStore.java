package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Price;
import com.homebase.ecom.entity.PriceEntity;
import org.chenile.utils.entity.service.EntityStore;

public interface PriceEntityStore extends EntityStore<Price> {


    Price findPriceByProductVariantId(String productVariantId);
}
