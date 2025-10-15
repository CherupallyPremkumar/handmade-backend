package com.homebase.ecom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Table(name = "hm_image")
public class ImageEntity extends BaseJpaEntity {

    private String url;
    private String altText;

    public String getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(String productVariantId) {
        this.productVariantId = productVariantId;
    }

    private String productVariantId;



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
