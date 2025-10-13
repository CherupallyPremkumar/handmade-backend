# Create a new product via State Machine
curl -X POST http://localhost:8081/api/state/products/create \
  -H "Content-Type: application/json" \
  -H "X-Tenant-Id: tenant1" \
  -d '{
    "name": "Premium Wireless Headphones",
    "description": "High-quality wireless headphones with noise cancellation",
    "price": 199.99,
    "onSale": true,
    "salePrice": 149.99,
    "stock": 50,
    "imageUrl": "https://example.com/headphones.jpg",
    "category": "Electronics",
    "tags": ["audio", "wireless", "premium"],
    "rating": 4.5,
    "featured": true,
    "tenantId": "tenant1"
  }'