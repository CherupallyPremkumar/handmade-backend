package com.handmade.ecommerce.reconciliation.service;

import com.handmade.ecommerce.reconciliation.model.Reconciliation;

public interface ReconciliationService {
	// Define your interface here
    public Reconciliation save(Reconciliation reconciliation);
    public Reconciliation retrieve(String id);
}
