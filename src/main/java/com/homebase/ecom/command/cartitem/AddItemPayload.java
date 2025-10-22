package com.homebase.ecom.command.cartitem;

import org.chenile.workflow.param.MinimalPayload;

public class AddItemPayload extends MinimalPayload {

    private String productVariantId;


    public String getProductVariantId() {
        return productVariantId;
    }
    public void setProductVariantId(String productVariantId) {
        this.productVariantId = productVariantId;
    }


}
