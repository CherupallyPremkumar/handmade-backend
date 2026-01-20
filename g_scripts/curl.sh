curl -X POST http://localhost:8080/api/q/get-product-details \
  -H "Content-Type: application/json" \
  -d '{
    "filters": {
      "id": "PROD-001"
    }
  }'