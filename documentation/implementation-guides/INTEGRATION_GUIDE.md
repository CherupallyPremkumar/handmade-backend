# Promotion & Analytics Management - Integration Guide

## 🎯 Module Integration Overview

This document explains how Promotion Management and Analytics Management integrate with Catalog Management.

---

## 🎁 Promotion Management Integration

### **Use Case**: "20% off all Pottery this week"

#### **Step 1: Create Category-Targeted Promotion**

```java
// In Promotion Management
Promotion potteryPromotion = new Promotion();
potteryPromotion.setPromotionId("PROMO-001");
potteryPromotion.setName("Pottery Week Sale");
potteryPromotion.setCode("POTTERY20");
potteryPromotion.setTargetType(PromotionTargetType.CATEGORY);
potteryPromotion.setTargetId("CAT-022"); // ← References Catalog's category ID
potteryPromotion.setDiscountType(DiscountType.PERCENTAGE);
potteryPromotion.setDiscountValue(new BigDecimal("20.00"));
potteryPromotion.setStartDate(LocalDateTime.of(2024, 12, 1, 0, 0));
potteryPromotion.setEndDate(LocalDateTime.of(2024, 12, 7, 23, 59));
potteryPromotion.setActive(true);
```

#### **Step 2: Query Catalog for Products in Category**

```java
// In Promotion Service
@Service
public class PromotionApplicationService {
    
    @Autowired
    private CatalogService catalogService; // ACL to Catalog Management
    
    public List<String> getApplicableProducts(Promotion promotion) {
        if (promotion.getTargetType() == PromotionTargetType.CATEGORY) {
            // Query Catalog for all products in this category
            return catalogService.getProductsByCategory(promotion.getTargetId());
        }
        // ... other target types
    }
}
```

#### **Step 3: Apply Discount at Checkout**

```java
// In Pricing Engine or Order Service
@Service
public class DiscountCalculationService {
    
    @Autowired
    private PromotionService promotionService;
    
    @Autowired
    private CatalogService catalogService;
    
    public BigDecimal calculateFinalPrice(String productId, BigDecimal originalPrice) {
        // Get product's categories from Catalog
        List<String> categoryIds = catalogService.getProductCategories(productId);
        
        // Find active promotions targeting these categories
        List<Promotion> applicablePromotions = promotionService
            .getActivePromotions()
            .stream()
            .filter(p -> p.getTargetType() == PromotionTargetType.CATEGORY)
            .filter(p -> categoryIds.contains(p.getTargetId()))
            .filter(Promotion::isCurrentlyActive)
            .sorted(Comparator.comparing(Promotion::getPriority).reversed())
            .collect(Collectors.toList());
        
        // Apply best discount
        BigDecimal finalPrice = originalPrice;
        for (Promotion promo : applicablePromotions) {
            BigDecimal discountedPrice = applyDiscount(originalPrice, promo);
            if (discountedPrice.compareTo(finalPrice) < 0) {
                finalPrice = discountedPrice;
            }
            if (!promo.getStackable()) {
                break; // Stop if promotion is not stackable
            }
        }
        
        return finalPrice;
    }
    
    private BigDecimal applyDiscount(BigDecimal price, Promotion promo) {
        switch (promo.getDiscountType()) {
            case PERCENTAGE:
                BigDecimal discount = price.multiply(promo.getDiscountValue())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
                BigDecimal discountedPrice = price.subtract(discount);
                
                // Apply max discount cap if set
                if (promo.getMaxDiscountAmount() != null && 
                    discount.compareTo(promo.getMaxDiscountAmount()) > 0) {
                    return price.subtract(promo.getMaxDiscountAmount());
                }
                return discountedPrice;
                
            case FIXED_AMOUNT:
                return price.subtract(promo.getDiscountValue());
                
            default:
                return price;
        }
    }
}
```

---

## 📊 Analytics Management Integration

### **Use Case**: Track category performance and update Catalog

#### **Step 1: Track Category Views**

```java
// In Analytics Management
@Service
public class AnalyticsTrackingService {
    
    @Autowired
    private CategoryAnalyticsRepository analyticsRepository;
    
    @EventListener
    public void onCategoryViewed(CategoryViewedEvent event) {
        LocalDate today = LocalDate.now();
        
        CategoryAnalytics analytics = analyticsRepository
            .findByCategoryIdAndDate(event.getCategoryId(), today)
            .orElse(new CategoryAnalytics());
        
        analytics.setCategoryId(event.getCategoryId());
        analytics.setAnalyticsDate(today);
        analytics.setViewCount(analytics.getViewCount() + 1);
        
        // Track unique visitors
        if (event.isUniqueVisitor()) {
            analytics.setUniqueVisitors(analytics.getUniqueVisitors() + 1);
        }
        
        analyticsRepository.save(analytics);
    }
    
    @EventListener
    public void onProductPurchased(ProductPurchasedEvent event) {
        // Get product's categories from Catalog
        List<String> categoryIds = catalogService.getProductCategories(event.getProductId());
        
        LocalDate today = LocalDate.now();
        
        // Update analytics for all categories this product belongs to
        for (String categoryId : categoryIds) {
            CategoryAnalytics analytics = analyticsRepository
                .findByCategoryIdAndDate(categoryId, today)
                .orElse(new CategoryAnalytics());
            
            analytics.setCategoryId(categoryId);
            analytics.setAnalyticsDate(today);
            analytics.setPurchaseCount(analytics.getPurchaseCount() + 1);
            
            analyticsRepository.save(analytics);
        }
    }
}
```

#### **Step 2: Calculate Popular Categories**

```java
// In Analytics Management
@Service
public class PopularityCalculationService {
    
    @Autowired
    private CategoryAnalyticsRepository analyticsRepository;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    @Scheduled(cron = "0 0 2 * * *") // Run daily at 2 AM
    public void calculatePopularCategories() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        
        List<CategoryAnalytics> analytics = analyticsRepository
            .findByAnalyticsDate(yesterday);
        
        // Calculate trends by comparing with previous day
        LocalDate twoDaysAgo = yesterday.minusDays(1);
        
        Map<String, PopularityScore> categoryScores = new HashMap<>();
        
        for (CategoryAnalytics current : analytics) {
            CategoryAnalytics previous = analyticsRepository
                .findByCategoryIdAndDate(current.getCategoryId(), twoDaysAgo)
                .orElse(null);
            
            // Determine trend
            Trend trend = Trend.STABLE;
            if (previous != null) {
                int scoreDiff = current.getPopularityScore() - previous.getPopularityScore();
                if (scoreDiff > 10) {
                    trend = Trend.UP;
                } else if (scoreDiff < -10) {
                    trend = Trend.DOWN;
                }
            }
            
            current.setTrend(trend);
            analyticsRepository.save(current);
            
            categoryScores.put(
                current.getCategoryId(),
                new PopularityScore(current.getPopularityScore(), trend)
            );
        }
        
        // Publish event for Catalog to consume
        eventPublisher.publishEvent(
            new PopularCategoriesCalculatedEvent(categoryScores)
        );
    }
}
```

#### **Step 3: Update Catalog Based on Analytics**

```java
// In Catalog Management
@Service
public class CatalogAnalyticsHandler {
    
    @Autowired
    private CatalogService catalogService;
    
    @EventListener
    public void onPopularCategoriesCalculated(PopularCategoriesCalculatedEvent event) {
        // Update featured status based on popularity
        event.getCategoryScores().forEach((categoryId, score) -> {
            Category category = catalogService.getCategory(categoryId);
            
            // Feature categories with high popularity
            if (score.getScore() > 90 && score.getTrend() == Trend.UP) {
                category.setFeatured(true);
            } else if (score.getScore() < 50) {
                category.setFeatured(false);
            }
            
            catalogService.updateCategory(categoryId, category);
        });
        
        // Update display order based on popularity
        List<String> sortedCategories = event.getCategoryScores()
            .entrySet()
            .stream()
            .sorted((a, b) -> b.getValue().getScore() - a.getValue().getScore())
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
        
        for (int i = 0; i < sortedCategories.size(); i++) {
            Category category = catalogService.getCategory(sortedCategories.get(i));
            category.setDisplayOrder(i + 1);
            catalogService.updateCategory(category.getCategoryId(), category);
        }
    }
}
```

---

## 🔄 Integration Flow Diagram

```
┌─────────────────────────────────────────────────────────┐
│              CATALOG MANAGEMENT                          │
│  Category: "Pottery" (CAT-022)                          │
│  - productCount: 80                                     │
│  - featured: false → true (updated by Analytics)        │
│  - displayOrder: 5 → 1 (updated by Analytics)           │
└──────────────┬──────────────────────────┬───────────────┘
               │                          │
               │ Provides category        │ Provides products
               │ structure                │ in category
               ▼                          ▼
┌──────────────────────────┐   ┌──────────────────────────┐
│  PROMOTION MANAGEMENT    │   │  ANALYTICS MANAGEMENT    │
├──────────────────────────┤   ├──────────────────────────┤
│                          │   │                          │
│ Promotion: PROMO-001     │   │ CategoryAnalytics:       │
│ - targetType: CATEGORY   │   │ - categoryId: "CAT-022"  │
│ - targetId: "CAT-022" ◄──┤   │ - viewCount: 1,250       │
│ - discount: 20%          │   │ - purchaseCount: 45      │
│                          │   │ - popularityScore: 95    │
│ Queries Catalog:         │   │ - trend: UP              │
│ └─> getProductsByCategory│   │                          │
│     Returns: [PROD-001,  │   │ Publishes Event:         │
│              PROD-005,   │   │ └─> PopularCategories    │
│              PROD-012]   │   │     CalculatedEvent      │
│                          │   │                          │
│ Applies 20% discount     │   │ Catalog listens and      │
│ to all products          │   │ updates featured status  │
└──────────────────────────┘   └──────────────────────────┘
```

---

## ✅ Summary

### **Domain Models Created**:

#### **Promotion Management**:
1. ✅ `Promotion` - Main aggregate root
2. ✅ `PromotionTargetType` - Enum (CATEGORY, PRODUCT, COLLECTION, ARTISAN, GLOBAL)
3. ✅ `DiscountType` - Enum (PERCENTAGE, FIXED_AMOUNT, BUY_X_GET_Y, FREE_SHIPPING)
4. ✅ `PromotionUsage` - Tracks usage by customers

#### **Analytics Management**:
1. ✅ `CategoryAnalytics` - Category performance metrics
2. ✅ `ProductAnalytics` - Product performance metrics
3. ✅ `Trend` - Enum (UP, DOWN, STABLE)

### **Integration Points**:
- ✅ Promotion → Catalog: Query products by category/collection
- ✅ Analytics → Catalog: Update featured status and display order
- ✅ Catalog → Promotion: Provide category structure for targeting
- ✅ Catalog → Analytics: Provide category/product structure for tracking

All modules communicate via **domain events** and **ACL** patterns for loose coupling! 🎯
