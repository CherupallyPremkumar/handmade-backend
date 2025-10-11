package com.homebase.admin.service;

import com.homebase.admin.dto.CreateOrderRequest;
import com.homebase.admin.dto.OrderDTO;
import com.homebase.admin.dto.OrderItemDTO;
import com.homebase.admin.entity.*;
import com.homebase.admin.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for customer-facing order operations
 * Handles checkout flow: Cart → Order → Payment
 */
@Service
public class UserOrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final PriceCalculationService priceCalculationService;

    // Tax and shipping configuration
    private static final BigDecimal TAX_RATE = new BigDecimal("0.18"); // 18% GST
    private static final BigDecimal FREE_SHIPPING_THRESHOLD = new BigDecimal("1000.00");
    private static final BigDecimal STANDARD_SHIPPING_COST = new BigDecimal("150.00");

    public UserOrderService(OrderRepository orderRepository, CartRepository cartRepository,
                           CartItemRepository cartItemRepository, ProductRepository productRepository,
                           CustomerRepository customerRepository, PriceCalculationService priceCalculationService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.priceCalculationService = priceCalculationService;
    }

    /**
     * Create order from cart items (Checkout flow)
     * 1. Validate cart items and stock
     * 2. Calculate totals (subtotal, tax, shipping)
     * 3. Create order with PENDING status
     * 4. Reserve stock
     * 5. Return order for payment processing
     */
    @Transactional
    public OrderDTO createOrderFromCart(CreateOrderRequest request, String tenantId) {
        // 1. Get or create customer
        Customer customer = getOrCreateCustomer(request, tenantId);

        // 2. Get cart items
        Cart cart = cartRepository.findByCustomerIdAndTenantIdAndStatus(
                customer.getId(), tenantId, Cart.CartStatus.OPEN
        ).orElseThrow(() -> new IllegalArgumentException("Cart is empty"));

        if (cart.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        // 3. Validate stock availability
        validateStockAvailability(cart.getCartItems());

        // 4. Create order
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setCustomer(customer);
        order.setCustomerName(request.getCustomerName());
        order.setCustomerEmail(request.getCustomerEmail());
        order.setCustomerPhone(request.getCustomerPhone());
        
        // Build full shipping address
        String fullAddress = buildShippingAddress(request);
        order.setShippingAddress(fullAddress);
        order.setCity(request.getCity());
        order.setState(request.getState());
        order.setPincode(request.getPincode());
        
        order.setTenantId(tenantId);
        order.setStatus(Order.OrderStatus.PENDING);
        order.setPaymentStatus(Order.PaymentStatus.PENDING);

        // 5. Create order items from cart items
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getSnapshotPrice()); // Use snapshot price from cart
            
            BigDecimal itemTotal = cartItem.getSnapshotPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            subtotal = subtotal.add(itemTotal);
            
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setSubtotal(subtotal);

        // 6. Calculate tax and shipping
        BigDecimal tax = subtotal.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal shippingCost = calculateShippingCost(subtotal);
        
        order.setTax(tax);
        order.setShippingCost(shippingCost);
        order.setTotal(subtotal.add(tax).add(shippingCost));

        // 7. Save order
        Order savedOrder = orderRepository.save(order);

        // 8. Reserve stock (reduce available stock)
        reserveStock(cart.getCartItems());

        // 9. Mark cart as CHECKED_OUT (don't delete yet, in case payment fails)
        cart.setStatus(Cart.CartStatus.CHECKED_OUT);
        cartRepository.save(cart);

        return convertToDTO(savedOrder);
    }

    /**
     * Get customer orders
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getCustomerOrders(Long customerId, String tenantId) {
        List<Order> orders = orderRepository.findByCustomerIdAndTenantIdOrderByCreatedAtDesc(
                customerId, tenantId
        );
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get order by ID
     */
    @Transactional(readOnly = true)
    public OrderDTO getOrderById(String orderNumber, String tenantId) {
        Order order = orderRepository.findByOrderNumberAndTenantId(orderNumber, tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return convertToDTO(order);
    }

    /**
     * Update order status after payment
     */
    @Transactional
    public void updateOrderAfterPayment(String orderNumber, String transactionId, 
                                       Payment.PaymentStatus paymentStatus, String tenantId) {
        Order order = orderRepository.findByOrderNumberAndTenantId(orderNumber, tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.setTransactionId(transactionId);

        if (paymentStatus == Payment.PaymentStatus.SUCCESS) {
            order.setPaymentStatus(Order.PaymentStatus.PAID);
            order.setStatus(Order.OrderStatus.CONFIRMED);
            
            // Clear the customer's cart after successful payment
            clearCustomerCart(order.getCustomer().getId(), tenantId);
        } else if (paymentStatus == Payment.PaymentStatus.FAILED) {
            order.setPaymentStatus(Order.PaymentStatus.FAILED);
            order.setStatus(Order.OrderStatus.PAYMENT_FAILED);
            
            // Restore stock if payment failed
            restoreStock(order.getItems());
        }

        orderRepository.save(order);
    }

    /**
     * Cancel order (only if not shipped)
     */
    @Transactional
    public void cancelOrder(String orderNumber, String tenantId) {
        Order order = orderRepository.findByOrderNumberAndTenantId(orderNumber, tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (order.getStatus() == Order.OrderStatus.SHIPPED || 
            order.getStatus() == Order.OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot cancel shipped or delivered order");
        }

        order.setStatus(Order.OrderStatus.CANCELLED);
        
        // Restore stock
        restoreStock(order.getItems());
        
        orderRepository.save(order);
    }

    // Helper methods

    private Customer getOrCreateCustomer(CreateOrderRequest request, String tenantId) {
        if (request.getCustomerId() != null) {
            return customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        }

        // Create new customer
        Customer customer = new Customer();
        customer.setName(request.getCustomerName());
        customer.setEmail(request.getCustomerEmail());
        customer.setPhone(request.getCustomerPhone());
        customer.setTenantId(tenantId);
        return customerRepository.save(customer);
    }

    private void validateStockAvailability(List<CartItem> cartItems) {
        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            if (product.getStock() < item.getQuantity()) {
                throw new IllegalStateException(
                    String.format("Insufficient stock for product: %s. Available: %d, Requested: %d",
                        product.getName(), product.getStock(), item.getQuantity())
                );
            }
        }
    }

    private void reserveStock(List<CartItem> cartItems) {
        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }
    }

    private void restoreStock(List<OrderItem> orderItems) {
        for (OrderItem item : orderItems) {
            Product product = item.getProductId() != null
                    ? productRepository.findById(item.getProductId())
                        .orElse(null)
                    : null;
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }
    }

    private BigDecimal calculateShippingCost(BigDecimal subtotal) {
        if (subtotal.compareTo(FREE_SHIPPING_THRESHOLD) >= 0) {
            return BigDecimal.ZERO;
        }
        return STANDARD_SHIPPING_COST;
    }

    private String buildShippingAddress(CreateOrderRequest request) {
        return String.format("%s, %s, %s, %s",
                request.getShippingAddress(),
                request.getCity(),
                request.getState(),
                request.getPincode());
    }

    private String generateOrderNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "ORD" + timestamp;
    }

    private void clearCustomerCart(Long customerId, String tenantId) {
        Cart cart = cartRepository.findByCustomerIdAndTenantIdAndStatus(
                customerId, tenantId, Cart.CartStatus.CHECKED_OUT
        ).orElse(null);

        if (cart != null) {
            cartItemRepository.deleteAll(cart.getCartItems());
            cart.getCartItems().clear();
            cart.setTotal(BigDecimal.ZERO);
            cart.setQuantity(0);
            cart.setStatus(Cart.CartStatus.OPEN);
            cartRepository.save(cart);
        }
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(String.valueOf(order.getId()));
        dto.setOrderNumber(order.getOrderNumber());
        dto.setCustomerId(order.getCustomer() != null ? order.getCustomer().getId() : null);
        dto.setCustomerName(order.getCustomerName());
        dto.setCustomerEmail(order.getCustomerEmail());
        dto.setCustomerPhone(order.getCustomerPhone());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setSubtotal(order.getSubtotal());
        dto.setTax(order.getTax());
        dto.setShippingCost(order.getShippingCost());
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus().name());
        dto.setPaymentStatus(order.getPaymentStatus().name());
        dto.setTransactionId(order.getTransactionId());
        dto.setCreatedAt(order.getCreatedAt() != null ? order.getCreatedAt().toString() : null);
        dto.setUpdatedAt(order.getUpdatedAt() != null ? order.getUpdatedAt().toString() : null);

        // Convert order items
        dto.setItems(order.getItems().stream()
                .map(this::convertOrderItemToDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    private OrderItemDTO convertOrderItemToDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(String.valueOf(item.getId()));
        dto.setProductId(item.getProductId());
        dto.setProductName(item.getProductName());
        dto.setPrice(item.getPrice());
        dto.setQuantity(item.getQuantity());
        dto.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        return dto;
    }
}
