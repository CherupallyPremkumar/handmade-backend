package com.homebase.ecom.command.price;

import java.util.UUID;

public class ActivatePriceCommand {

    private String productVariantId;


    public String getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(String productVariantId) {
        this.productVariantId = productVariantId;
    }
}
