package com.handmade.ecommerce.governance.service;

import com.handmade.ecommerce.governance.model.Governance;

public interface GovernanceService {
	// Define your interface here
    public Governance save(Governance governance);
    public Governance retrieve(String id);
}
