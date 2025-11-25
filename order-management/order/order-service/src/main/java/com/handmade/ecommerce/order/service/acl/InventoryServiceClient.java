package com.handmade.ecommerce.order.service.acl;

import org.springframework.stereotype.Service;
import java.util.List;

/**
 * InventoryServiceClient - Anti-Corruption Layer
 * Protects Order Management from Inventory Management's internal model
 */
@Service
public class InventoryServiceClient {

    // @Autowired
    // private InventoryService inventoryService; // From inventory-management

    /**
     * Reserve stock for order items
     * 
     * @param orderId Order ID
     * @param items   List of items with SKU and quantity
     * @return Reservation result
     */
    public ReservationResult reserveStock(String orderId, List<OrderItemReservation> items) {
        // TODO: Call inventory-management service
        // inventoryService.reserveStock(orderId, items);

        // For now, return mock result
        return new ReservationResult(true, "Stock reserved successfully");
    }

    /**
     * Release reserved stock (e.g., order cancelled)
     * 
     * @param orderId Order ID
     */
    public void releaseReservedStock(String orderId) {
        // TODO: Call inventory-management service
        // inventoryService.releaseReservation(orderId);
    }

    /**
     * Check stock availability
     * 
     * @param sku      Product SKU
     * @param quantity Quantity needed
     * @return true if available
     */
    public boolean checkStockAvailability(String sku, int quantity) {
        // TODO: Call inventory-management service
        // return inventoryService.isAvailable(sku, quantity);
        return true; // Mock
    }

    /**
     * DTO for order item reservation
     */
    public static class OrderItemReservation {
        private String sku;
        private int quantity;

        public OrderItemReservation(String sku, int quantity) {
            this.sku = sku;
            this.quantity = quantity;
        }

        public String getSku() {
            return sku;
        }

        public int getQuantity() {
            return quantity;
        }
    }

    /**
     * DTO for reservation result
     */
    public static class ReservationResult {
        private boolean success;
        private String message;

        public ReservationResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}
