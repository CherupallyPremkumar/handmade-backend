package com.handmade.ecommerce.order.model;

import com.handmade.ecommerce.common.model.Money;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import org.springframework.security.access.method.P;

import java.time.LocalDate;
import java.util.List;

public class PurchaseOrder extends Order {
        private Long poId;
        @Column(unique = true, nullable = false)
        private String poNumber; // PK: Unique identifier
        @Column(nullable = false)
        private String vendorReferenceNumber;
        private Long supplierId;
        private String approvedBy;// FK: Supplier
        private LocalDate orderDate; // Date PO created
        private LocalDate expectedDeliveryDate;
        private String status; // e.g., Draft, Awaiting Receipt, Received
        private Long receivingWarehouseId; // FK: Warehouse
        @Embedded
        @AttributeOverrides({
                        @AttributeOverride(name = "amount", column = @Column(name = "freight_cost_amount")),
                        @AttributeOverride(name = "currency", column = @Column(name = "freight_cost_currency"))
        })
        private Money estimatedFreightCost;
        @Embedded
        @AttributeOverrides({
                        @AttributeOverride(name = "amount", column = @Column(name = "total_cost_amount")),
                        @AttributeOverride(name = "currency", column = @Column(name = "total_cost_currency"))
        })
        private Money totalCost;// Calculated total
        private List<PurchaseOrderItem> items; // One-to-many relationship with PO items

        // Constructor
        public PurchaseOrder(Long poId, Long supplierId, LocalDate orderDate,
                        LocalDate expectedDeliveryDate, String status,
                        Long receivingWarehouseId,
                        List<PurchaseOrderItem> items) {
                this.poId = poId;
                this.supplierId = supplierId;
                this.orderDate = orderDate;
                this.expectedDeliveryDate = expectedDeliveryDate;
                this.status = status;
                this.receivingWarehouseId = receivingWarehouseId;
                this.items = items;
        }

        // Getters and setters
        public Long getPoId() {
                return poId;
        }

        public void setPoId(Long poId) {
                this.poId = poId;
        }

        public Long getSupplierId() {
                return supplierId;
        }

        public void setSupplierId(Long supplierId) {
                this.supplierId = supplierId;
        }

        public LocalDate getOrderDate() {
                return orderDate;
        }

        public void setOrderDate(LocalDate orderDate) {
                this.orderDate = orderDate;
        }

        public LocalDate getExpectedDeliveryDate() {
                return expectedDeliveryDate;
        }

        public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
                this.expectedDeliveryDate = expectedDeliveryDate;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public Long getReceivingWarehouseId() {
                return receivingWarehouseId;
        }

        public void setReceivingWarehouseId(Long receivingWarehouseId) {
                this.receivingWarehouseId = receivingWarehouseId;
        }

        public List<PurchaseOrderItem> getItems() {
                return items;
        }

        public void setItems(List<PurchaseOrderItem> items) {
                this.items = items;
        }
}