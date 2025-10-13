package com.homebase.ecom.entity;

import com.homebase.ecom.entity.CartItemEntity;
import com.homebase.ecom.entity.CustomerEntity;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "carts")
public class CartEntity extends AbstractJpaStateEntity {


    private int quantity;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal salePrice = BigDecimal.ZERO;
    
    @Enumerated(EnumType.STRING)
    private CartStatus status = CartStatus.OPEN;

    String customerId;

    public enum CartStatus {
        OPEN, CHECKED_OUT, ABANDONED
    }


    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }


}
