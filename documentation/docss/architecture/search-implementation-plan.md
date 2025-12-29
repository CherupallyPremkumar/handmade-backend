# Search Management Implementation Plan

**Module**: `handmade-search-management`  
**Purpose**: Unified search infrastructure for products, sellers, and orders  
**Pattern**: Plugin-based provider architecture

---

## Current State

### Existing Structure
```
handmade-search-management/
├── pom.xml (parent)
├── search-api/
│   ├── pom.xml
│   └── src/ (empty - needs implementation)
└── search-core/
    ├── pom.xml
    └── src/ (empty - needs implementation)
```

### Artifact IDs
- ✅ Parent: `search-management`
- ✅ API: `handmade-search-api`
- ✅ Core: `handmade-search-core`

---

## Architecture Overview

### Design Pattern: Plugin-Based Provider

Similar to `notification-management` and `shipping-management`, search uses a **provider pattern**:

```
┌─────────────────────────────────────┐
│         Search API                  │
│  - SearchService interface          │
│  - SearchRequest/Response DTOs      │
│  - SearchProvider SPI               │
└──────────────┬──────────────────────┘
               │
               ↓
┌─────────────────────────────────────┐
│         Search Core                 │
│  - SearchProviderRegistry           │
│  - Default provider selection       │
│  - Query parsing                    │
└──────────────┬──────────────────────┘
               │
               ↓
┌─────────────────────────────────────┐
│      Provider Implementations       │
│  - ElasticsearchProvider (primary)  │
│  - SolrProvider (future)            │
│  - AlgoliaProvider (future)         │
└─────────────────────────────────────┘
```

---

## Module Structure

### 1. search-api (Domain Models & Interfaces)

**Package**: `com.handmade.ecommerce.search.api`

#### Domain Models
```java
// Search request
public class SearchRequest {
    private String query;
    private String entityType; // PRODUCT, SELLER, ORDER
    private Map<String, Object> filters;
    private List<String> facets;
    private Pagination pagination;
    private SortCriteria sort;
}

// Search response
public class SearchResponse<T> {
    private List<T> results;
    private long totalHits;
    private Map<String, List<FacetValue>> facets;
    private SearchMetadata metadata;
}

// Indexing
public class IndexRequest {
    private String entityType;
    private String entityId;
    private Map<String, Object> document;
}
```

#### Service Interface
```java
public interface SearchService {
    <T> SearchResponse<T> search(SearchRequest request, Class<T> resultType);
    void index(IndexRequest request);
    void bulkIndex(List<IndexRequest> requests);
    void delete(String entityType, String entityId);
    void reindex(String entityType);
}
```

#### Provider SPI
```java
public interface SearchProvider {
    String getProviderName();
    boolean supports(String entityType);
    <T> SearchResponse<T> executeSearch(SearchRequest request, Class<T> resultType);
    void indexDocument(IndexRequest request);
    void deleteDocument(String entityType, String entityId);
}
```

---

### 2. search-core (Provider Registry & Orchestration)

**Package**: `com.handmade.ecommerce.search.core`

#### Components
```java
// Provider registry
@Component
public class SearchProviderRegistry {
    private final Map<String, SearchProvider> providers;
    
    public SearchProvider getProvider(String entityType) {
        // Select provider based on entity type
    }
}

// Default implementation
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchProviderRegistry registry;
    
    @Override
    public <T> SearchResponse<T> search(SearchRequest request, Class<T> resultType) {
        SearchProvider provider = registry.getProvider(request.getEntityType());
        return provider.executeSearch(request, resultType);
    }
}
```

---

### 3. elasticsearch-provider (Primary Implementation)

**New Module**: `handmade-search-management/elasticsearch-provider`

**Dependencies**:
```xml
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>elasticsearch-rest-high-level-client</artifactId>
</dependency>
```

**Implementation**:
```java
@Component
public class ElasticsearchProvider implements SearchProvider {
    @Autowired
    private RestHighLevelClient elasticsearchClient;
    
    @Override
    public String getProviderName() {
        return "elasticsearch";
    }
    
    @Override
    public <T> SearchResponse<T> executeSearch(SearchRequest request, Class<T> resultType) {
        // Build Elasticsearch query
        // Execute search
        // Map results to response
    }
}
```

---

## Entity-Specific Indexing

### Product Search Index

**Index Name**: `products`

**Mapping**:
```json
{
  "properties": {
    "id": { "type": "keyword" },
    "name": { "type": "text", "analyzer": "standard" },
    "description": { "type": "text" },
    "category": { "type": "keyword" },
    "tags": { "type": "keyword" },
    "price": { "type": "double" },
    "sellerId": { "type": "keyword" },
    "status": { "type": "keyword" },
    "createdAt": { "type": "date" },
    "attributes": { "type": "nested" }
  }
}
```

**Indexing Strategy**:
- Index on product creation/update
- Async indexing via event listeners
- Bulk reindex capability

### Seller Search Index

**Index Name**: `sellers`

**Mapping**:
```json
{
  "properties": {
    "id": { "type": "keyword" },
    "businessName": { "type": "text" },
    "country": { "type": "keyword" },
    "status": { "type": "keyword" },
    "categories": { "type": "keyword" },
    "rating": { "type": "float" },
    "verificationStatus": { "type": "keyword" }
  }
}
```

### Order Search Index

**Index Name**: `orders`

**Mapping**:
```json
{
  "properties": {
    "id": { "type": "keyword" },
    "customerId": { "type": "keyword" },
    "sellerId": { "type": "keyword" },
    "status": { "type": "keyword" },
    "totalAmount": { "type": "double" },
    "createdAt": { "type": "date" },
    "items": { "type": "nested" }
  }
}
```

---

## Implementation Phases

### Phase 1: Core Infrastructure ✅ (Week 1)

#### Tasks
- [x] Module structure exists
- [ ] Define search-api domain models
- [ ] Create SearchService interface
- [ ] Create SearchProvider SPI
- [ ] Implement SearchProviderRegistry
- [ ] Create base SearchServiceImpl

**Files to Create**:
1. `search-api/src/main/java/com/handmade/ecommerce/search/api/SearchRequest.java`
2. `search-api/src/main/java/com/handmade/ecommerce/search/api/SearchResponse.java`
3. `search-api/src/main/java/com/handmade/ecommerce/search/api/SearchService.java`
4. `search-api/src/main/java/com/handmade/ecommerce/search/api/SearchProvider.java`
5. `search-core/src/main/java/com/handmade/ecommerce/search/core/SearchProviderRegistry.java`
6. `search-core/src/main/java/com/handmade/ecommerce/search/core/SearchServiceImpl.java`

---

### Phase 2: Elasticsearch Provider (Week 2)

#### Tasks
- [ ] Create elasticsearch-provider module
- [ ] Add Elasticsearch dependencies
- [ ] Implement ElasticsearchProvider
- [ ] Create index templates
- [ ] Implement query builders
- [ ] Add faceting support

**New Module Structure**:
```
elasticsearch-provider/
├── pom.xml
└── src/main/java/com/handmade/ecommerce/search/elasticsearch/
    ├── ElasticsearchProvider.java
    ├── ElasticsearchConfiguration.java
    ├── QueryBuilder.java
    └── IndexManager.java
```

---

### Phase 3: Product Search Integration (Week 3)

#### Tasks
- [ ] Create ProductSearchDocument DTO
- [ ] Implement product indexing listener
- [ ] Create product search queries
- [ ] Add faceted search (category, price range)
- [ ] Implement autocomplete
- [ ] Add relevance scoring

**Integration Points**:
```java
// In product-service
@EventListener
public void onProductCreated(ProductCreatedEvent event) {
    searchService.index(new IndexRequest()
        .entityType("PRODUCT")
        .entityId(event.getProductId())
        .document(mapToSearchDocument(event.getProduct())));
}
```

---

### Phase 4: Seller & Order Search (Week 4)

#### Tasks
- [ ] Implement seller search
- [ ] Implement order search
- [ ] Add admin search APIs
- [ ] Create search analytics
- [ ] Performance optimization

---

## API Design

### REST Endpoints

```
POST /search/products
POST /search/sellers
POST /search/orders
POST /search/autocomplete
POST /search/suggest

POST /admin/search/reindex/{entityType}
GET  /admin/search/stats
```

### Example Request
```json
POST /search/products
{
  "query": "handmade leather bag",
  "filters": {
    "category": "accessories",
    "priceRange": { "min": 1000, "max": 5000 },
    "sellerId": "SEL-123"
  },
  "facets": ["category", "price", "seller"],
  "pagination": { "page": 1, "size": 20 },
  "sort": { "field": "relevance", "order": "desc" }
}
```

### Example Response
```json
{
  "results": [
    {
      "id": "PROD-456",
      "name": "Handmade Leather Tote Bag",
      "price": 2500,
      "category": "accessories",
      "sellerId": "SEL-123",
      "score": 0.95
    }
  ],
  "totalHits": 42,
  "facets": {
    "category": [
      { "value": "accessories", "count": 30 },
      { "value": "bags", "count": 12 }
    ],
    "priceRange": [
      { "value": "1000-2000", "count": 15 },
      { "value": "2000-5000", "count": 27 }
    ]
  },
  "metadata": {
    "took": 23,
    "provider": "elasticsearch"
  }
}
```

---

## Configuration

### application.yml
```yaml
search:
  provider: elasticsearch
  elasticsearch:
    hosts: localhost:9200
    username: elastic
    password: ${ELASTICSEARCH_PASSWORD}
    index-prefix: handmade
  indexing:
    async: true
    batch-size: 100
  cache:
    enabled: true
    ttl: 300
```

---

## Dependencies to Add

### search-api/pom.xml
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

### search-core/pom.xml
```xml
<dependencies>
    <dependency>
        <groupId>com.handmade.ecommerce</groupId>
        <artifactId>handmade-search-api</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
</dependencies>
```

### elasticsearch-provider/pom.xml
```xml
<dependencies>
    <dependency>
        <groupId>com.handmade.ecommerce</groupId>
        <artifactId>handmade-search-api</artifactId>
    </dependency>
    <dependency>
        <groupId>org.elasticsearch.client</groupId>
        <artifactId>elasticsearch-rest-high-level-client</artifactId>
        <version>7.17.0</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
</dependencies>
```

---

## Testing Strategy

### Unit Tests
- SearchProviderRegistry selection logic
- Query builder correctness
- DTO mapping

### Integration Tests
- Elasticsearch connectivity
- Index creation/deletion
- Search query execution
- Faceting accuracy

### Performance Tests
- Search latency (< 100ms)
- Indexing throughput (> 1000 docs/sec)
- Concurrent search load

---

## Future Enhancements

### Phase 5+
- [ ] Search analytics dashboard
- [ ] Personalized search results
- [ ] ML-based relevance tuning
- [ ] Multi-language support
- [ ] Synonym management
- [ ] Search suggestions
- [ ] Typo tolerance
- [ ] Geo-search for local sellers

---

## Success Metrics

- ✅ Search latency < 100ms (p95)
- ✅ Indexing lag < 5 seconds
- ✅ Search relevance > 80% (user satisfaction)
- ✅ Zero downtime reindexing
- ✅ Support 10M+ documents

---

## Next Steps

1. **Review & Approve** this plan
2. **Phase 1**: Implement core API and provider pattern
3. **Phase 2**: Integrate Elasticsearch
4. **Phase 3**: Product search integration
5. **Phase 4**: Seller & order search
6. **Deploy** to staging for testing
