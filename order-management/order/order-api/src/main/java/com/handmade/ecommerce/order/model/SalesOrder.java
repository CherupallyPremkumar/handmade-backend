package com.handmade.ecommerce.order.model;

import com.handmade.ecommerce.common.model.Money;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class SalesOrder extends Order {

        @Embedded
        @AttributeOverrides({
                        @AttributeOverride(name = "amount", column = @Column(name = "subtotal_amount")),
                        @AttributeOverride(name = "currency", column = @Column(name = "subtotal_currency"))
        })
        private Money subTotal;

        // 2. Tax Amount
        // Maps Money.amount -> tax_amount, Money.currency -> tax_currency
        @Embedded
        @AttributeOverrides({
                        @AttributeOverride(name = "amount", column = @Column(name = "total_tax_amount")),
                        @AttributeOverride(name = "currency", column = @Column(name = "total_tax_currency"))
        })
        private Money totalTax;

        @Embedded
        @AttributeOverrides({
                        @AttributeOverride(name = "amount", column = @Column(name = "grand_total_amount")),
                        @AttributeOverride(name = "currency", column = @Column(name = "grand_total_currency"))
        })
        private Money grandTotal;

        @Embedded
        @AttributeOverrides({
                        @AttributeOverride(name = "amount", column = @Column(name = "total_discount_amount")),
                        @AttributeOverride(name = "currency", column = @Column(name = "total_discount_currency"))
        })
        private Money totalDiscount;

        @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<SalesOrderItem> items = new ArrayList<>();
}
