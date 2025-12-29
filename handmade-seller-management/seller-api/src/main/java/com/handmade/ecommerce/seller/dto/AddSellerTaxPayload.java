package com.handmade.ecommerce.seller.dto.command;

import com.handmade.ecommerce.seller.dto.TaxInfoRequest;
import org.chenile.workflow.param.MinimalPayload;

import java.util.List;

/**
    Customized Payload for the activate event.
*/
public class AddSellerTaxPayload extends MinimalPayload{

    private List<TaxInfoRequest> taxInfoRequests;

    public List<TaxInfoRequest> getTaxInfoRequests() {
        return taxInfoRequests;
    }

    public void setTaxInfoRequests(List<TaxInfoRequest> taxInfoRequests) {
        this.taxInfoRequests = taxInfoRequests;
    }
}
