package com.handmade.ecommerce.seller.dto.command;

import com.handmade.ecommerce.seller.dto.SellerPaymentInfoRequest;
import org.chenile.workflow.param.MinimalPayload;

import java.util.List;

public class AddSellerPaymentPayload extends MinimalPayload {
    private List<SellerPaymentInfoRequest> paymentInfoRequests;

    public List<SellerPaymentInfoRequest> getPaymentInfoRequests() {
        return paymentInfoRequests;
    }

    public void setPaymentInfoRequests(List<SellerPaymentInfoRequest> paymentInfoRequests) {
        this.paymentInfoRequests = paymentInfoRequests;
    }
}
