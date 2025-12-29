package com.handmade.ecommerce.razorpay.service;

import com.handmade.ecommerce.razorpay.model.Razorpay;

public interface RazorpayService {
	// Define your interface here
    public Razorpay save(Razorpay razorpay);
    public Razorpay retrieve(String id);
}
