package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.PriceLine;

public interface PriceLineEntityStore {


   PriceLine findByPriceIdAndCurrency(String priceId, String country);
}
