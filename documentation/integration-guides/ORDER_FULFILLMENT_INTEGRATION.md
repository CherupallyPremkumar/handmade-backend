# Order Fulfillment Cluster - Integration Guide

## 🎯 Overview

This guide explains how the **Order Fulfillment Cluster** works - the integration between Order Management, Inventory Management, Payment Management, and Shipping Management.

---

## 📦 Modules in This Cluster

| Module | Purpose | Key Entities |
|--------|---------|--------------|
| **Order Management** | Orchestrates the order lifecycle | `Order`, `OrderStatus`, `ShippingAddress` |
| **Inventory Management** | Manages stock and reservations | `InventoryItem`, `StockReservation` |
| **Payment Management** | Processes payments | `PaymentTransaction`, `PaymentMethod` |
| **Shipping Management** | Handles shipment and delivery | `Shipment`, `Carrier`, `TrackingInfo` |

---

## 🔗 Integration Patterns Used

### **1. Reference by ID (Loose Coupling)**

Each module only stores IDs to reference other modules:

```java
// In Order Management
class Order {
    private String orderId;
    private String customerId;      // Reference to Customer Management
    private List<OrderItem> items;  // Contains SKU (reference to Product)
}

class OrderItem {
    private String sku;      // Reference to Inventory
    private int quantity;
    private BigDecimal price;
}
```

**Why?** Order doesn't need to know Inventory's internal structure (warehouse bins, locations, etc.).

---

### **2. Anti-Corruption Layer (ACL)**

ACLs translate between module boundaries:

```java
// In Order Management
@Service
public class InventoryServiceClient {  // ACL
    
    public ReservationResult reserveStock(String orderId, List<OrderItemReservation> items) {
        // Translate Order's model to Inventory's API
        // Order doesn't know about Inventory's internal complexity
        return inventoryService.reserveStock(orderId, items);
    }
}
```

**Benefits**:
- Order doesn't depend on Inventory's internal model
- If Inventory changes its database schema, Order is unaffected
- Clear contract between modules

---

### **3. Domain Events (Async Communication)**

Modules communicate via events:

```java
// Payment publishes event
eventPublisher.publishEvent(new PaymentCapturedEvent(orderId, paymentId));

// Order listens
@EventListener
public void onPaymentCaptured(PaymentCapturedEvent event) {
    Order order = orderRepository.findById(event.getOrderId());
    order.setStatus(OrderStatus.PAID);
    orderRepository.save(order);
    
    // Publish next event
    eventPublisher.publishEvent(new OrderPaidEvent(order.getOrderId()));
}
```

---

## 🚀 Complete Flow Walkthrough

### **Scenario**: Customer orders a handmade ceramic mug

#### **Step 1: Customer Places Order**

```java
// Customer adds items to cart and clicks "Checkout"
CreateOrderCommand command = new CreateOrderCommand();
command.setCustomerId("CUST-001");
command.setItems(List.of(
    new OrderItemDTO("SKU-CERAMIC-MUG", 2, new BigDecimal("25.00"))
));
command.setShippingAddress(shippingAddress);

Order order = orderService.createOrder(command);
// Order created with status: DRAFT
```

#### **Step 2: Reserve Stock**

```java
// Order Management calls Inventory via ACL
List<OrderItemReservation> reservations = order.getItems().stream()
    .map(item -> new OrderItemReservation(item.getSku(), item.getQuantity()))
    .collect(Collectors.toList());

ReservationResult result = inventoryServiceClient.reserveStock(
    order.getOrderId(), 
    reservations
);

if (result.isSuccess()) {
    order.setStatus(OrderStatus.PENDING_PAYMENT);
} else {
    throw new InsufficientStockException("Stock not available");
}
```

**In Inventory Management**:
```java
@Service
public class InventoryService {
    
    public ReservationResult reserveStock(String orderId, List<OrderItemReservation> items) {
        for (OrderItemReservation item : items) {
            InventoryItem inventory = inventoryRepository.findBySku(item.getSku());
            
            // Reserve stock
            boolean reserved = inventory.reserveStock(item.getQuantity());
            if (!reserved) {
                return new ReservationResult(false, "Insufficient stock for " + item.getSku());
            }
            
            // Create reservation record
            StockReservation reservation = new StockReservation();
            reservation.setOrderId(orderId);
            reservation.setSku(item.getSku());
            reservation.setQuantityReserved(item.getQuantity());
            reservation.setStatus(ReservationStatus.RESERVED);
            reservationRepository.save(reservation);
            
            inventoryRepository.save(inventory);
        }
        
        // Publish event
        eventPublisher.publishEvent(new StockReservedEvent(orderId));
        
        return new ReservationResult(true, "Stock reserved");
    }
}
```

#### **Step 3: Initiate Payment**

```java
// Order Management calls Payment via ACL
PaymentInitiationResult paymentResult = paymentServiceClient.initiatePayment(
    order.getOrderId(),
    order.getGrandTotal(),
    "USD"
);

// Redirect customer to payment gateway
return paymentResult.getPaymentUrl();
```

**In Payment Management**:
```java
@Service
public class PaymentService {
    
    public PaymentInitiationResult initiatePayment(String orderId, BigDecimal amount, String currency) {
        // Create payment transaction
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setOrderId(orderId);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setStatus(PaymentStatus.PENDING);
        paymentRepository.save(transaction);
        
        // Call external payment gateway (Stripe, Razorpay)
        String paymentUrl = paymentGatewayClient.createCheckoutSession(transaction);
        
        return new PaymentInitiationResult(
            transaction.getPaymentId(),
            paymentUrl,
            "PENDING"
        );
    }
}
```

#### **Step 4: Payment Captured**

```java
// Payment gateway webhook callback
@PostMapping("/webhook/payment-success")
public void handlePaymentSuccess(@RequestBody PaymentWebhookPayload payload) {
    PaymentTransaction transaction = paymentRepository.findByGatewayTransactionId(
        payload.getTransactionId()
    );
    
    // Update payment status
    transaction.capture();
    paymentRepository.save(transaction);
    
    // Publish event
    eventPublisher.publishEvent(new PaymentCapturedEvent(
        transaction.getOrderId(),
        transaction.getPaymentId()
    ));
}
```

#### **Step 5: Order Updates to PAID**

```java
// In Order Management
@EventListener
public void onPaymentCaptured(PaymentCapturedEvent event) {
    Order order = orderRepository.findById(event.getOrderId());
    order.setStatus(OrderStatus.PAID);
    orderRepository.save(order);
    
    // Publish event for shipping
    eventPublisher.publishEvent(new OrderPaidEvent(order.getOrderId()));
}
```

#### **Step 6: Create Shipment**

```java
// In Shipping Management
@EventListener
public void onOrderPaid(OrderPaidEvent event) {
    // Get order details via ACL
    OrderDTO order = orderServiceClient.getOrder(event.getOrderId());
    
    // Create shipment
    Shipment shipment = new Shipment();
    shipment.setOrderId(event.getOrderId());
    shipment.setCarrier(Carrier.FEDEX);
    shipment.setStatus(ShipmentStatus.PENDING);
    shipmentRepository.save(shipment);
    
    // Generate shipping label via carrier API
    String trackingNumber = carrierServiceClient.createLabel(shipment, order.getShippingAddress());
    shipment.setTrackingNumber(trackingNumber);
    shipment.setStatus(ShipmentStatus.LABEL_CREATED);
    shipmentRepository.save(shipment);
    
    // Publish event
    eventPublisher.publishEvent(new ShipmentCreatedEvent(
        shipment.getShipmentId(),
        event.getOrderId(),
        trackingNumber
    ));
}
```

#### **Step 7: Order Updates to SHIPPED**

```java
// In Order Management
@EventListener
public void onShipmentCreated(ShipmentCreatedEvent event) {
    Order order = orderRepository.findById(event.getOrderId());
    order.setStatus(OrderStatus.SHIPPED);
    order.setTrackingNumber(event.getTrackingNumber());
    orderRepository.save(order);
    
    // Notify customer
    notificationService.sendShipmentNotification(order);
}
```

---

## 📊 Domain Models Created

### **Order Management**:
1. ✅ `OrderStatus` - Enum (DRAFT, PENDING_PAYMENT, PAID, SHIPPED, DELIVERED)
2. ✅ `ShippingAddress` - Value object

### **Inventory Management**:
1. ✅ `InventoryItem` - Aggregate root (SKU, quantities, warehouse)
2. ✅ `StockReservation` - Entity (tracks reserved stock)
3. ✅ `ReservationStatus` - Enum (RESERVED, RELEASED, FULFILLED)

### **Shipping Management**:
1. ✅ `Shipment` - Aggregate root
2. ✅ `Carrier` - Enum (FEDEX, UPS, DHL, etc.)
3. ✅ `ShipmentStatus` - Enum (PENDING, IN_TRANSIT, DELIVERED)
4. ✅ `TrackingInfo` - Value object

### **ACLs Created**:
1. ✅ `InventoryServiceClient` - Order → Inventory
2. ✅ `PaymentServiceClient` - Order → Payment
3. ✅ `ShippingServiceClient` - Order → Shipping

---

## 🎯 Key Benefits

1. **Loose Coupling**: Modules only know each other by ID
2. **Independent Evolution**: Each module can change internally
3. **Clear Contracts**: ACLs define explicit interfaces
4. **Event-Driven**: Async communication via domain events
5. **Testable**: Easy to mock ACLs

---

## 📈 Next Steps

1. **Implement Services**: Create service implementations for each module
2. **Add Repositories**: JPA repositories for data access
3. **Implement Event Handlers**: Complete event listener logic
4. **Add REST APIs**: Expose endpoints for each module
5. **Integration Testing**: Test the complete flow end-to-end

---

## 🔍 Visual Diagrams

See `order-fulfillment-flow.puml` for complete sequence diagram showing all interactions.

---

This architecture ensures scalability, maintainability, and clear separation of concerns! 🎯
