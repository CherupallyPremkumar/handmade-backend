# Payment Executor Module

## Overview

The **payment-executor** module provides a clean abstraction layer for integrating with different Payment Service Providers (PSPs) like Razorpay, Stripe, PayPal, etc.

## Architecture

```
payment-executor/
└── payment-executor-api/          # Interface and models
    ├── PaymentExecutor.java       # Main interface
    ├── PaymentExecutorFactory.java # Factory for selecting PSP
    ├── PaymentExecutorException.java
    └── model/
        ├── CheckoutSession.java
        ├── PaymentStatus.java
        ├── RefundRequest.java
        └── RefundResponse.java
```

## Key Components

### 1. PaymentExecutor Interface

The core interface that all PSP implementations must implement:

```java
public interface PaymentExecutor {
    CheckoutSession createCheckoutSession(...);
    PaymentStatus checkPaymentStatus(String pspPaymentId);
    RefundResponse initiateRefund(RefundRequest request);
    PaymentStatus checkRefundStatus(String pspRefundId);
    PaymentStatus capturePayment(String pspPaymentId, BigDecimal amount);
    PaymentStatus cancelPayment(String pspPaymentId);
    boolean validateWebhookSignature(String payload, String signature, String secret);
    String getPspType();
}
```

### 2. PaymentExecutorFactory

Factory pattern implementation that automatically discovers all PaymentExecutor implementations via Spring dependency injection:

```java
@Autowired
private PaymentExecutorFactory executorFactory;

// Get executor for specific PSP
PaymentExecutor executor = executorFactory.getExecutor("RAZORPAY");
```

### 3. Model Classes

- **CheckoutSession**: Contains session ID and checkout URL from PSP
- **PaymentStatus**: Enum representing payment/refund status
- **RefundRequest**: Request object for refund operations
- **RefundResponse**: Response from PSP after refund initiation

## Usage

### In Orchestrator

```java
@Service
public class PaymentProcessingServiceImpl {
    
    @Autowired
    private PaymentExecutorFactory executorFactory;
    
    public PaymentOrderResponse processPaymentOrder(PaymentOrderRequest req) {
        // Get appropriate executor
        PaymentExecutor executor = executorFactory.getExecutor(req.getPspType());
        
        // Create checkout session
        CheckoutSession session = executor.createCheckoutSession(
            req.getPaymentOrderId(),
            req.getAmount(),
            req.getCurrency(),
            req.getSellerId(),
            req.getBuyerId(),
            redirectUrl,
            webhookUrl
        );
        
        return buildResponse(session);
    }
}
```

### Implementing a New PSP

To add support for a new PSP, create an implementation in the PSP-specific module:

```java
@Service("paypalExecutor")
public class PayPalExecutor implements PaymentExecutor {
    
    @Override
    public CheckoutSession createCheckoutSession(...) {
        // PayPal-specific implementation
    }
    
    @Override
    public String getPspType() {
        return "PAYPAL";
    }
    
    // Implement other methods...
}
```

The factory will automatically discover this implementation via Spring's component scanning.

## Benefits

✅ **Loose Coupling**: Orchestrator depends on interface, not implementations  
✅ **Easy PSP Switching**: Change PSP without modifying orchestrator code  
✅ **Testability**: Mock PaymentExecutor for unit tests  
✅ **Extensibility**: Add new PSPs by implementing the interface  
✅ **Strategy Pattern**: Clean separation of PSP-specific logic  

## Dependencies

This module depends on:
- Spring Framework (for @Component, @Autowired)
- Spring Boot Starter

Other modules that use this:
- `orchestrator` - Uses PaymentExecutor to create checkout sessions
- `razorpay` - Implements RazorpayExecutor
- `stripe` - Implements StripeExecutor

## Testing

```java
@Test
public void testCreateCheckoutSession() {
    PaymentExecutor executor = executorFactory.getExecutor("RAZORPAY");
    
    CheckoutSession session = executor.createCheckoutSession(
        "order-123", 
        new BigDecimal("100.00"), 
        "INR",
        "seller-1",
        "buyer-1",
        "https://redirect.url",
        "https://webhook.url"
    );
    
    assertNotNull(session.getSessionId());
    assertNotNull(session.getCheckoutUrl());
}
```

## Future Enhancements

- Add circuit breaker for PSP calls
- Implement retry logic with exponential backoff
- Add metrics and monitoring
- Support for batch operations
- Async payment status polling
