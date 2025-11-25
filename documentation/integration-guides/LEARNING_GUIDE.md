# Complete Integration Guide - Learning Path

## 📚 How All Modules Are Related

This guide explains the relationships between **Catalog Management**, **Promotion Management**, and **Analytics Management** with visual diagrams and code examples.

---

## 🎯 Learning Path

### **Level 1: Understanding Individual Modules**

#### **1. Catalog Management** (Product Organization)
**Purpose**: Organize products into categories and collections

**Key Entities**:
- `Category` - Hierarchical structure (Jewelry → Necklaces → Silver Necklaces)
- `Collection` - Curated groups (Holiday Gifts, New Arrivals)
- `CatalogItem` - Bridge to Product Management

**What it does**:
- Organizes products into browsable categories
- Creates featured collections
- Provides navigation structure

---

#### **2. Promotion Management** (Discounts & Campaigns)
**Purpose**: Apply discounts to products, categories, or collections

**Key Entities**:
- `Promotion` - Discount rules and targeting
- `PromotionTargetType` - What to target (CATEGORY, PRODUCT, COLLECTION)
- `DiscountType` - How to discount (PERCENTAGE, FIXED_AMOUNT)

**What it does**:
- Creates promotional campaigns
- Targets specific categories/products
- Tracks promotion usage

---

#### **3. Analytics Management** (Performance Tracking)
**Purpose**: Track engagement and calculate popularity

**Key Entities**:
- `CategoryAnalytics` - Category performance metrics
- `ProductAnalytics` - Product performance metrics
- `Trend` - Trending direction (UP, DOWN, STABLE)

**What it does**:
- Tracks views, clicks, purchases
- Calculates popularity scores
- Identifies trending categories/products

---

### **Level 2: Understanding Relationships**

#### **Relationship 1: Catalog ← Promotion** (Query Pattern)

```
Promotion needs to know:
"Which products are in the Pottery category?"

Flow:
1. Promotion: targetType = CATEGORY, targetId = "CAT-022"
2. Promotion queries Catalog: getProductsByCategory("CAT-022")
3. Catalog returns: ["PROD-001", "PROD-005", "PROD-012", ...]
4. Promotion applies 20% discount to all these products
```

**Code Example**:
```java
// In Promotion Service
List<String> productIds = catalogService.getProductsByCategory("CAT-022");
for (String productId : productIds) {
    applyDiscount(productId, 20.0);
}
```

---

#### **Relationship 2: Catalog → Analytics** (Event Pattern)

```
Analytics needs to know:
"Which category was viewed?"

Flow:
1. Customer views "Pottery" category
2. Catalog publishes: CategoryViewedEvent(categoryId: "CAT-022")
3. Analytics listens and increments viewCount for CAT-022
```

**Code Example**:
```java
// In Catalog Service
eventPublisher.publishEvent(new CategoryViewedEvent("CAT-022"));

// In Analytics Service
@EventListener
public void onCategoryViewed(CategoryViewedEvent event) {
    CategoryAnalytics analytics = getOrCreate(event.getCategoryId());
    analytics.setViewCount(analytics.getViewCount() + 1);
    save(analytics);
}
```

---

#### **Relationship 3: Analytics → Catalog** (Feedback Pattern)

```
Catalog needs to know:
"Which categories are popular?"

Flow:
1. Analytics calculates daily: CAT-022 has popularityScore = 95, trend = UP
2. Analytics publishes: PopularCategoriesCalculatedEvent
3. Catalog listens and updates: CAT-022.featured = true, displayOrder = 1
```

**Code Example**:
```java
// In Analytics Service
Map<String, PopularityScore> scores = calculatePopularity();
eventPublisher.publishEvent(new PopularCategoriesCalculatedEvent(scores));

// In Catalog Service
@EventListener
public void onPopularCategoriesCalculated(PopularCategoriesCalculatedEvent event) {
    event.getCategoryScores().forEach((categoryId, score) -> {
        if (score.getScore() > 90) {
            Category cat = getCategory(categoryId);
            cat.setFeatured(true);
            updateCategory(cat);
        }
    });
}
```

---

### **Level 3: Complete Integration Flow**

#### **Real-World Example: "20% off all Pottery this week"**

```
Step 1: Admin creates promotion
  └─> Promotion: targetType=CATEGORY, targetId="CAT-022", discount=20%

Step 2: Customer views Pottery category
  └─> Catalog publishes CategoryViewedEvent
      └─> Analytics increments viewCount

Step 3: Customer views a pottery product
  └─> Catalog queries: "What categories is this product in?"
      └─> Returns: ["CAT-022"]
  └─> Pricing Engine queries Promotion: "Any promotions for CAT-022?"
      └─> Returns: [20% off]
  └─> Shows: Original $50 → Sale $40

Step 4: Customer purchases
  └─> Promotion increments usage count
  └─> Order publishes ProductPurchasedEvent
      └─> Analytics increments purchaseCount for CAT-022

Step 5: Daily analytics calculation
  └─> Analytics calculates: CAT-022 popularityScore = 95, trend = UP
  └─> Analytics publishes PopularCategoriesCalculatedEvent
      └─> Catalog updates: CAT-022.featured = true, displayOrder = 1

Result: Pottery category is now featured on homepage!
```

---

## 📊 Visual Diagrams

### **Diagram 1: Complete Integration** (`complete-integration.puml`)
Shows all entities and their relationships across modules.

### **Diagram 2: Integration Flow Sequence** (`integration-flow-sequence.puml`)
Step-by-step sequence diagram of the real-world example.

### **Diagram 3: Module Dependencies** (`module-dependencies.puml`)
High-level component diagram showing data flow.

---

## 🔑 Key Integration Patterns

### **1. Reference by ID** (Loose Coupling)
```java
// ❌ BAD: Direct object reference
private Product product;

// ✅ GOOD: String reference
private String productId;
```

**Why**: Modules can evolve independently.

---

### **2. Anti-Corruption Layer** (ACL)
```java
// Catalog doesn't use Product's complex model directly
public class ProductServiceClient {
    public CatalogProductDTO getProductForCatalog(String productId) {
        Product product = productService.getProduct(productId);
        return new CatalogProductDTO(
            product.getId(),
            extractSimpleName(product), // Translation!
            extractSimplePrice(product)  // Translation!
        );
    }
}
```

**Why**: Protects Catalog from Product's internal changes.

---

### **3. Domain Events** (Async Communication)
```java
// Publisher (Catalog)
eventPublisher.publishEvent(new CategoryViewedEvent(categoryId));

// Subscriber (Analytics)
@EventListener
public void onCategoryViewed(CategoryViewedEvent event) {
    // Handle event
}
```

**Why**: Modules don't know about each other directly.

---

## 🎓 Learning Exercises

### **Exercise 1: Trace the Flow**
Follow a customer journey:
1. Customer searches "handmade pottery"
2. Views "Pottery" category
3. Clicks on a product
4. Sees 20% discount
5. Makes purchase

**Question**: Which modules are involved at each step?

### **Exercise 2: Add New Feature**
Implement: "Buy 2 get 1 free in Jewelry category"

**Steps**:
1. Create Promotion with targetType=CATEGORY, targetId="CAT-001"
2. Set discountType=BUY_X_GET_Y, buyQuantity=2, getQuantity=1
3. Query Catalog for products in Jewelry
4. Apply discount logic in Pricing Engine

### **Exercise 3: Analytics Dashboard**
Build a dashboard showing:
- Top 10 categories by popularity
- Trending products
- Promotion effectiveness

**Data sources**:
- CategoryAnalytics (popularity scores)
- ProductAnalytics (trending scores)
- PromotionUsage (discount amounts)

---

## ✅ Quick Reference

### **When to Query Catalog**:
- ✅ Getting products in a category (Promotion)
- ✅ Getting product categories (Pricing, Analytics)
- ✅ Getting category hierarchy (Search)

### **When to Publish Events**:
- ✅ Category viewed (Catalog → Analytics)
- ✅ Product purchased (Order → Analytics)
- ✅ Popularity calculated (Analytics → Catalog)

### **When to Use ACL**:
- ✅ Fetching product details for display (Catalog → Product)
- ✅ Translating complex models to simple DTOs

---

## 🚀 Next Steps

1. **Study the PlantUML diagrams** - Visual understanding
2. **Read the code examples** - Implementation details
3. **Try the exercises** - Hands-on practice
4. **Implement services** - Build the actual logic
5. **Test integrations** - Verify event flow

All modules work together to create a cohesive e-commerce experience! 🎯
