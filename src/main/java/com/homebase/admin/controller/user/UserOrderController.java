package com.homebase.admin.controller.user;

import com.homebase.admin.dto.CreateOrderRequest;
import com.homebase.admin.dto.OrderDTO;
import com.homebase.admin.service.UserOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Customer-facing order controller
 * Handles checkout and order management
 */
@RestController
@RequestMapping("/api/user/order")
public class UserOrderController {

    private final UserOrderService userOrderService;

    public UserOrderController(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    /**
     * POST /api/user/order?tenantId={tenantId}
     * Create order from cart (Checkout)
     * 
     * Flow:
     * 1. Validate cart items and stock
     * 2. Create order with customer details
     * 3. Calculate totals (subtotal, tax, shipping)
     * 4. Reserve stock
     * 5. Return order for payment processing
     */
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            @RequestBody CreateOrderRequest request,
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        OrderDTO order = userOrderService.createOrderFromCart(request, tenantId);
        return ResponseEntity.ok(order);
    }

    /**
     * GET /api/user/order?customerId={customerId}&tenantId={tenantId}
     * Get all orders for a customer
     */
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getCustomerOrders(
            @RequestParam Long customerId,
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        List<OrderDTO> orders = userOrderService.getCustomerOrders(customerId, tenantId);
        return ResponseEntity.ok(orders);
    }

    /**
     * GET /api/user/order/{orderNumber}?tenantId={tenantId}
     * Get order details by order number
     */
    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrderDTO> getOrderByNumber(
            @PathVariable String orderNumber,
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        OrderDTO order = userOrderService.getOrderById(orderNumber, tenantId);
        return ResponseEntity.ok(order);
    }

    /**
     * POST /api/user/order/{orderNumber}/cancel?tenantId={tenantId}
     * Cancel an order (only if not shipped)
     */
    @PostMapping("/{orderNumber}/cancel")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable String orderNumber,
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        userOrderService.cancelOrder(orderNumber, tenantId);
        return ResponseEntity.noContent().build();
    }
}
