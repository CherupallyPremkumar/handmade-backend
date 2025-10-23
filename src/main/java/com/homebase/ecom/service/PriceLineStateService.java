package com.homebase.ecom.service;

import com.homebase.ecom.command.priceline.PriceLinePayload;
import com.homebase.ecom.domain.PriceLine;

public interface PriceLineStateService {
    PriceLine createPriceLine( PriceLinePayload priceLinePayload);
}
