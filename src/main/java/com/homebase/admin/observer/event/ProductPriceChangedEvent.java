package com.homebase.admin.observer.event;

import java.math.BigDecimal;

public class ProductPriceChangedEvent {
    private final String productId;
    private final BigDecimal oldPrice;
    private final BigDecimal newPrice;
    private final Boolean oldOnSale;
    private final Boolean newOnSale;
    private final BigDecimal oldSalePrice;
    private final BigDecimal newSalePrice;
    
    public ProductPriceChangedEvent(String productId, BigDecimal oldPrice, BigDecimal newPrice,
                                   Boolean oldOnSale, Boolean newOnSale,
                                   BigDecimal oldSalePrice, BigDecimal newSalePrice) {
        this.productId = productId;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.oldOnSale = oldOnSale;
        this.newOnSale = newOnSale;
        this.oldSalePrice = oldSalePrice;
        this.newSalePrice = newSalePrice;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public BigDecimal getOldPrice() {
        return oldPrice;
    }
    
    public BigDecimal getNewPrice() {
        return newPrice;
    }
    
    public Boolean getOldOnSale() {
        return oldOnSale;
    }
    
    public Boolean getNewOnSale() {
        return newOnSale;
    }
    
    public BigDecimal getOldSalePrice() {
        return oldSalePrice;
    }
    
    public BigDecimal getNewSalePrice() {
        return newSalePrice;
    }
    
    public BigDecimal getEffectiveOldPrice() {
        return (oldOnSale != null && oldOnSale && oldSalePrice != null) ? oldSalePrice : oldPrice;
    }
    
    public BigDecimal getEffectiveNewPrice() {
        return (newOnSale != null && newOnSale && newSalePrice != null) ? newSalePrice : newPrice;
    }
}
