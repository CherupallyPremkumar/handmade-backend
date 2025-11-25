# Catalog Management - Module Connections

## 🔗 How Catalog Management Connects to Other Modules

### **1. Product Management** (Customer-Supplier Relationship)

#### **Connection Point**: `CatalogItem.productId`

```java
// In CatalogItem.java
@Column(name = "product_id", nullable = false, length = 50)
private String productId;  // ← This is the ONLY reference to Product Management
```

#### **Integration Pattern**: Anti-Corruption Layer (ACL)

**Why ACL?**
- Catalog Management should NOT depend on Product Management's internal model
- Product changes shouldn't break Catalog
- Catalog only needs minimal product info (id, name, price for display)

#### **Example Service Integration**:

```java
// In your CatalogService implementation
public class CatalogServiceImpl {
    
    @Autowired
    private ProductServiceClient productServiceClient; // ACL
    
    public CatalogItemDTO getCatalogItemWithProduct(String catalogItemId) {
        CatalogItem catalogItem = catalogItemRepository.findById(catalogItemId);
        
        // Fetch product details via ACL
        ProductDTO product = productServiceClient.getProduct(catalogItem.getProductId());
        
        // Combine catalog + product data
        return new CatalogItemDTO(catalogItem, product);
    }
}
```

---

### **2. Search Module** (If you have one)

#### **Connection**: Catalog provides structure for search facets

```java
// Catalog publishes category structure for search indexing
public interface CatalogEventPublisher {
    void publishCategoryCreated(Category category);
    void publishProductCategorized(String productId, List<String> categoryIds);
}
```

---

### **3. Analytics Module**

#### **Connection**: Bidirectional

**Catalog → Analytics**:
- Track category views
- Track collection clicks
- Monitor merchandising effectiveness

**Analytics → Catalog**:
- Popular categories influence featured status
- Trending products auto-added to "Trending" collection

```java
// Example: Dynamic collection based on analytics
Collection trendingCollection = new Collection();
trendingCollection.setType(CollectionType.DYNAMIC);
trendingCollection.setRuleExpression("analytics.trending_score > 80");
trendingCollection.setAutoUpdate(true);
```

---

### **4. Promotion Management**

#### **Connection**: Category-wide promotions

```java
// Promotion can target entire category
Promotion categoryPromotion = new Promotion();
categoryPromotion.setTargetType(TargetType.CATEGORY);
categoryPromotion.setTargetId("jewelry"); // category slug
categoryPromotion.setDiscountPercent(20);
```

---

## 📊 Database Relationships

### **Tables Created**:

1. **categories** - Category hierarchy
2. **collections** - Product collections
3. **catalog_items** - Product catalog metadata
4. **product_category_mapping** - Products ↔ Categories (many-to-many)
5. **collection_product_mapping** - Collections ↔ Products (many-to-many)
6. **catalog_item_tags** - Merchandising tags

### **Foreign Key Relationships**:

```sql
-- Categories (self-referencing)
categories.parent_id → categories.category_id

-- Product-Category Mapping
product_category_mapping.category_id → categories.category_id
product_category_mapping.product_id → products.product_id (in product-management)

-- Collection-Product Mapping
collection_product_mapping.collection_id → collections.collection_id
collection_product_mapping.product_id → products.product_id (in product-management)

-- Catalog Items
catalog_items.product_id → products.product_id (in product-management)
```

---

## 🎯 Domain Events

### **Events Published by Catalog Management**:

```java
// When category structure changes
CategoryCreatedEvent
CategoryUpdatedEvent
CategoryDeletedEvent
CategoryMovedEvent

// When products are categorized
ProductCategorizedEvent
ProductRemovedFromCategoryEvent

// When collections change
CollectionCreatedEvent
CollectionUpdatedEvent
ProductAddedToCollectionEvent
ProductRemovedFromCollectionEvent
```

### **Events Consumed by Catalog Management**:

```java
// From Product Management
ProductPublishedEvent → Auto-create CatalogItem
ProductUnpublishedEvent → Deactivate CatalogItem
ProductDeletedEvent → Remove from all categories/collections

// From Analytics
TrendingProductsCalculatedEvent → Update "Trending" collection
```

---

## 🔧 Service Interfaces You Should Implement

### **CatalogService.java** (in catalog-api)

```java
package com.handmade.ecommerce.catalog.service;

import com.handmade.ecommerce.catalog.model.*;
import java.util.List;

public interface CatalogService {
    
    // Category Management
    Category createCategory(Category category);
    Category updateCategory(String categoryId, Category category);
    void deleteCategory(String categoryId);
    Category getCategory(String categoryId);
    List<Category> getCategoryHierarchy();
    List<Category> getChildCategories(String parentId);
    
    // Collection Management
    Collection createCollection(Collection collection);
    Collection updateCollection(String collectionId, Collection collection);
    void deleteCollection(String collectionId);
    Collection getCollection(String collectionId);
    List<Collection> getActiveCollections();
    
    // Product Categorization
    void addProductToCategory(String productId, String categoryId, boolean isPrimary);
    void removeProductFromCategory(String productId, String categoryId);
    List<String> getProductCategories(String productId);
    List<String> getProductsByCategory(String categoryId);
    
    // Collection Management
    void addProductToCollection(String collectionId, String productId);
    void removeProductFromCollection(String collectionId, String productId);
    List<String> getProductsByCollection(String collectionId);
    
    // Catalog Item Management
    CatalogItem createCatalogItem(CatalogItem catalogItem);
    CatalogItem updateCatalogItem(String catalogItemId, CatalogItem catalogItem);
    CatalogItem getCatalogItem(String catalogItemId);
    CatalogItem getCatalogItemByProductId(String productId);
    
    // Merchandising
    void setFeaturedInCategory(String categoryId, String productId, boolean featured);
    List<String> getFeaturedProducts(String categoryId);
}
```

---

## 📦 Dependencies to Add

### **In catalog-service/pom.xml**:

```xml
<dependencies>
    <!-- Catalog API -->
    <dependency>
        <groupId>com.handmade.ecommerce.catalog</groupId>
        <artifactId>catalog-api</artifactId>
    </dependency>
    
    <!-- Product API (for ACL) -->
    <dependency>
        <groupId>com.handmade.ecommerce.product</groupId>
        <artifactId>product-api</artifactId>
        <scope>compile</scope>
    </dependency>
    
    <!-- Spring Boot -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- Chenile -->
    <dependency>
        <groupId>org.chenile</groupId>
        <artifactId>chenile-core</artifactId>
    </dependency>
</dependencies>
```

---

## ✅ Summary

### **Domain Models Created**:
1. ✅ `Category` - Hierarchical category structure
2. ✅ `CategoryMetadata` - SEO and display settings
3. ✅ `Collection` - Product collections
4. ✅ `CollectionType` - Collection types enum
5. ✅ `CatalogItem` - Product catalog metadata
6. ✅ `ProductCategoryMapping` - Product-Category relationships
7. ✅ `CollectionProductMapping` - Collection-Product relationships

### **Key Connection**:
- **`CatalogItem.productId`** → Links to Product Management
- **Anti-Corruption Layer** → Protects Catalog from Product changes
- **Domain Events** → Loose coupling between modules

### **Next Steps for You**:
1. Implement `CatalogService` interface
2. Create JPA repositories for each entity
3. Implement event handlers for Product events
4. Create REST controllers for catalog APIs
5. Add caching for category hierarchy (frequently accessed)

Now you have a complete domain model! You can implement the services yourself. 🎯
