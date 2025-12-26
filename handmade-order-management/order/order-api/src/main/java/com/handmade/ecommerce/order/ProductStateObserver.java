package com.handmade.ecommerce.order;

import com.handmade.ecommerce.event.EventObserver;
import com.handmade.ecommerce.product.dto.OnHideProductPayload;

public interface ProductStateObserver extends EventObserver<OnHideProductPayload> {
}
