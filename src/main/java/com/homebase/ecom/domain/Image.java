package com.homebase.ecom.domain;

import org.chenile.utils.entity.model.BaseEntity;

public class Image extends BaseEntity {

    private String url;
    private String altText;
    private String productVariantId;

    public String getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(String productVariantId) {
        this.productVariantId = productVariantId;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }


}
