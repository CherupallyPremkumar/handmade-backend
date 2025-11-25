# Plugin Architecture Guide - Carriers & Payment Gateways

## 🎯 Overview

This guide explains the **Plugin Architecture** for carriers and payment gateways. Each carrier and payment provider is a **separate, independent module** that can be deployed, updated, or removed without affecting other parts of the system.

---

## 📦 Module Structure

### **Shipping Management**
```
shipping-management/
  ├── shipping-core/              # Core plugin interface
  │   ├── pom.xml
  │   └── src/main/java/
  │       ├── CarrierPlugin.java
  │       └── CarrierPluginRegistry.java
  │
  ├── carrier-fedex/              # FedEx plugin (separate module)
  │   ├── pom.xml
  │   └── src/main/java/
  │       └── FedExCarrierPlugin.java
  │
  ├── carrier-ups/                # UPS plugin (separate module)
  │   ├── pom.xml
  │   └── src/main/java/
  │       └── UPSCarrierPlugin.java
  │
  └── carrier-dhl/                # DHL plugin (separate module)
      ├── pom.xml
      └── src/main/java/
          └── DHLCarrierPlugin.java
```

### **Payment Management**
```
payment-management/
  ├── payment-core/               # Core plugin interface
  │   ├── pom.xml
  │   └── src/main/java/
  │       ├── PaymentGatewayPlugin.java
  │       └── PaymentGatewayPluginRegistry.java
  │
  ├── gateway-stripe/             # Stripe plugin (separate module)
  │   ├── pom.xml
  │   └── src/main/java/
  │       └── StripeGatewayPlugin.java
  │
  └── gateway-razorpay/           # Razorpay plugin (separate module)
      ├── pom.xml
      └── src/main/java/
          └── RazorpayGatewayPlugin.java
```

---

## 🚀 How It Works

### **1. Plugin Discovery (Automatic)**

Spring automatically discovers all plugins at startup:

```java
// In CarrierPluginRegistry
public CarrierPluginRegistry(List<CarrierPlugin> carrierPlugins) {
    // Spring auto-injects ALL implementations of CarrierPlugin
    for (CarrierPlugin plugin : carrierPlugins) {
        if (plugin.isEnabled()) {
            plugins.put(plugin.getCarrierCode(), plugin);
            System.out.println("✅ Registered: " + plugin.getCarrierName());
        }
    }
}
```

**Console Output**:
```
✅ Registered carrier plugin: FEDEX (FedEx)
✅ Registered carrier plugin: UPS (UPS)
✅ Registered carrier plugin: DHL (DHL Express)
📦 Total active carriers: 3
```

### **2. Using Plugins in Services**

```java
@Service
public class ShippingService {
    
    @Autowired
    private CarrierPluginRegistry carrierRegistry;
    
    public Shipment createShipment(String orderId, String carrierCode) {
        // Get the right carrier plugin
        CarrierPlugin carrier = carrierRegistry.getCarrier(carrierCode);
        
        // Use the plugin
        String trackingNumber = carrier.createShippingLabel(shipment, address);
        
        return shipment;
    }
}
```

### **3. Configuration-Driven**

Enable/disable plugins via `application.yml`:

```yaml
# Carrier Configuration
carrier:
  fedex:
    enabled: true
    api:
      key: "your-fedex-api-key"
      endpoint: "https://api.fedex.com"
  
  ups:
    enabled: true
    api:
      key: "your-ups-api-key"
  
  dhl:
    enabled: false  # Disabled - won't be loaded
    api:
      key: "your-dhl-api-key"

# Payment Gateway Configuration
payment:
  stripe:
    enabled: true
    api:
      key: "sk_test_..."
    webhook:
      secret: "whsec_..."
  
  razorpay:
    enabled: true
    key:
      id: "rzp_test_..."
      secret: "..."
```

---

## ✅ Adding a New Carrier (e.g., Amazon Logistics)

### **Step 1: Create New Module**

```bash
cd shipping-management
mkdir -p carrier-amazon/src/main/java/com/handmade/ecommerce/shipping/carrier/amazon
```

### **Step 2: Create POM**

```xml
<!-- carrier-amazon/pom.xml -->
<project>
    <parent>
        <groupId>com.handmade.ecommerce</groupId>
        <artifactId>shipping-management</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <artifactId>carrier-amazon</artifactId>
    <name>Amazon Logistics Carrier Plugin</name>

    <dependencies>
        <dependency>
            <groupId>com.handmade.ecommerce</groupId>
            <artifactId>shipping-core</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
</project>
```

### **Step 3: Implement Plugin**

```java
package com.handmade.ecommerce.shipping.carrier.amazon;

import com.handmade.ecommerce.shipping.core.CarrierPlugin;
import org.springframework.stereotype.Component;

@Component
public class AmazonLogisticsCarrierPlugin implements CarrierPlugin {
    
    @Override
    public String getCarrierCode() {
        return "AMAZON_LOGISTICS";
    }
    
    @Override
    public String getCarrierName() {
        return "Amazon Logistics";
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    // Implement other methods...
}
```

### **Step 4: Add to Parent POM**

```xml
<!-- shipping-management/pom.xml -->
<modules>
    <module>shipping-core</module>
    <module>carrier-fedex</module>
    <module>carrier-ups</module>
    <module>carrier-dhl</module>
    <module>carrier-amazon</module>  <!-- NEW -->
</modules>
```

### **Step 5: Build & Deploy**

```bash
mvn clean install
```

**That's it!** ✅ The new carrier is automatically discovered and available.

---

## ✅ Adding a New Payment Gateway (e.g., PayPal)

### **Step 1-3: Same as carrier**

Create module `gateway-paypal` with `PayPalGatewayPlugin.java`

### **Step 4: Implement Plugin**

```java
@Component
public class PayPalGatewayPlugin implements PaymentGatewayPlugin {
    
    @Override
    public String getGatewayCode() {
        return "PAYPAL";
    }
    
    @Override
    public String getGatewayName() {
        return "PayPal";
    }
    
    @Override
    public PaymentInitiationResult initiatePayment(String orderId, BigDecimal amount, String currency) {
        // Call PayPal API
        return new PaymentInitiationResult(...);
    }
    
    // Implement other methods...
}
```

---

## 🎯 Benefits of Plugin Architecture

| Benefit | Description |
|---------|-------------|
| **Independent Deployment** | Deploy new carrier without redeploying entire app |
| **Third-Party Extensions** | External vendors can create plugins |
| **Easy Testing** | Test each plugin in isolation |
| **Configuration-Driven** | Enable/disable via config, no code changes |
| **Scalability** | Add unlimited carriers/gateways |
| **Maintainability** | Each plugin is self-contained |

---

## 📊 Comparison: Before vs After

### **Before (Monolithic)**
```
❌ Adding new carrier:
1. Modify enum
2. Add if/else logic
3. Recompile entire module
4. Redeploy everything
5. Risk breaking existing carriers
```

### **After (Plugin Architecture)**
```
✅ Adding new carrier:
1. Create new module
2. Implement CarrierPlugin interface
3. Add to parent POM
4. Deploy ONLY the new module
5. Zero risk to existing carriers
```

---

## 🔧 Usage Examples

### **Example 1: Get All Available Carriers**

```java
@RestController
public class CarrierController {
    
    @Autowired
    private CarrierPluginRegistry carrierRegistry;
    
    @GetMapping("/api/carriers")
    public Map<String, String> getCarriers() {
        return carrierRegistry.getCarrierDetails();
        // Returns: {"FEDEX": "FedEx", "UPS": "UPS", "DHL": "DHL Express"}
    }
}
```

### **Example 2: Calculate Shipping Costs for All Carriers**

```java
public List<ShippingOption> getShippingOptions(ShippingAddress from, ShippingAddress to, double weight) {
    List<ShippingOption> options = new ArrayList<>();
    
    for (CarrierPlugin carrier : carrierRegistry.getAllCarriers()) {
        if (carrier.supportsAddress(to)) {
            BigDecimal cost = carrier.calculateShippingCost(from, to, weight);
            int days = carrier.getEstimatedDeliveryDays(from, to);
            
            options.add(new ShippingOption(
                carrier.getCarrierCode(),
                carrier.getCarrierName(),
                cost,
                days
            ));
        }
    }
    
    return options.stream()
        .sorted(Comparator.comparing(ShippingOption::getCost))
        .collect(Collectors.toList());
}
```

**Output**:
```json
[
  {"carrier": "UPS", "name": "UPS", "cost": 14.00, "days": 3},
  {"carrier": "FEDEX", "name": "FedEx", "cost": 17.50, "days": 3},
  {"carrier": "DHL", "name": "DHL Express", "cost": 26.00, "days": 6}
]
```

### **Example 3: Process Payment with Selected Gateway**

```java
@Service
public class PaymentService {
    
    @Autowired
    private PaymentGatewayPluginRegistry gatewayRegistry;
    
    public String processPayment(String orderId, BigDecimal amount, String gatewayCode) {
        // Get the selected gateway
        PaymentGatewayPlugin gateway = gatewayRegistry.getGateway(gatewayCode);
        
        // Initiate payment
        PaymentInitiationResult result = gateway.initiatePayment(orderId, amount, "USD");
        
        // Return checkout URL
        return result.getCheckoutUrl();
    }
}
```

---

## 🎯 Summary

**Plugin Architecture** provides:
- ✅ **True modularity** - each carrier/gateway is independent
- ✅ **Easy extensibility** - add new plugins without touching existing code
- ✅ **Configuration-driven** - enable/disable via config
- ✅ **Third-party support** - external vendors can create plugins
- ✅ **Zero downtime** - deploy new plugins independently

**This is the most scalable approach for your e-commerce platform!** 🚀
