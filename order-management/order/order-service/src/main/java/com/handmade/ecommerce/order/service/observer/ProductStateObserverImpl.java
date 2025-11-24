package com.handmade.ecommerce.order.service.observer;

import com.handmade.ecommerce.event.EventBus;
import com.handmade.ecommerce.order.ProductStateObserver;
import com.handmade.ecommerce.product.dto.OnHideProductPayload;


public class ProductStateObserverImpl implements ProductStateObserver {

    public ProductStateObserverImpl(EventBus eventBus) {
        // Register itself to the bus
        eventBus.register(OnHideProductPayload.class, this);
    }
    @Override
    public void onEvent(OnHideProductPayload event) {

    }
}
