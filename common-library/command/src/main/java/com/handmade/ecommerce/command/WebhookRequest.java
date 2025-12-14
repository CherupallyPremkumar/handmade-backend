package com.handmade.ecommerce.command;

import java.io.Serializable;

public class WebhookRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String payload;
    private String signature;
    private String pspType;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPspType() {
        return pspType;
    }

    public void setPspType(String pspType) {
        this.pspType = pspType;
    }
}
