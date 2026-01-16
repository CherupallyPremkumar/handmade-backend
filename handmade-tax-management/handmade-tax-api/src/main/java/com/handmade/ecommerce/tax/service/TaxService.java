package com.handmade.ecommerce.tax.service;

import com.handmade.ecommerce.tax.model.Tax;

public interface TaxService {
	// Define your interface here
    public Tax save(Tax tax);
    public Tax retrieve(String id);
}
