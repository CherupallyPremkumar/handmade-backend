package com.handmade.ecommerce.orderline.dto;

import org.chenile.workflow.param.MinimalPayload;

/**
    Customized Payload for the addOrderLine event.
*/
public class AddOrderLineOrderlinePayload extends MinimalPayload{
     private String productId;
     private int quantity;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
