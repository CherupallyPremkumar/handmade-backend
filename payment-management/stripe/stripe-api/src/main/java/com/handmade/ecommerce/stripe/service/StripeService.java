package com.handmade.ecommerce.stripe.service;

import com.handmade.ecommerce.stripe.model.Stripe;

public interface StripeService {
	// Define your interface here
    public Stripe save(Stripe stripe);
    public Stripe retrieve(String id);
}
