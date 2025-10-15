Feature: Order Management API Tests

  Scenario: Create a new order
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/api/orders" with payload
    """
    {
      "customerId": "customer-001",
      "customerName": "Alice Johnson",
      "customerEmail": "alice.johnson@example.com",
      "customerPhone": "+1-555-0201",
      "shippingAddress": "123 Main Street, Apt 4B",
      "city": "New York",
      "state": "NY",
      "pincode": "10001",
      "items": [
        {
          "productId": "variant-001",
          "productName": "Wireless Bluetooth Headphones",
          "quantity": 1,
          "price": 199.99
        }
      ],
      "subtotal": 199.99,
      "tax": 16.00,
      "shippingCost": 9.99,
      "total": 225.98
    }
    """
    Then the http status code is 201
    And success is true
    And the REST response key "orderNumber" exists
    And the REST response key "status" is "PENDING"
    And the REST response key "total" is "225.98"

  Scenario: Get order by ID
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/orders/order-001"
    Then the http status code is 200
    And success is true
    And the REST response key "id" is "order-001"
    And the REST response key "orderNumber" is "ORD-2024-001"
    And the REST response key "status" exists

  Scenario: Get order by order number
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/orders/number/ORD-2024-001"
    Then the http status code is 200
    And success is true
    And the REST response key "orderNumber" is "ORD-2024-001"

  Scenario: Update order status to CONFIRMED
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I PUT a REST request to URL "/api/orders/order-001/status" with payload
    """
    {
      "status": "CONFIRMED"
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "status" is "CONFIRMED"

  Scenario: Update order status to SHIPPED
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I PUT a REST request to URL "/api/orders/order-002/status" with payload
    """
    {
      "status": "SHIPPED"
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "status" is "SHIPPED"

  Scenario: Get customer orders
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/orders/customer/customer-001"
    Then the http status code is 200
    And success is true
    And the REST response is an array
    And the REST response array size is greater than "0"

  Scenario: Search orders by status
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/q/order" with payload
    """
    {
      "numRowsInPage": 10,
      "pageNum": 1,
      "filters": {
        "tenant_id": "tenant0",
        "status": "DELIVERED"
      }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "numRowsReturned" is greater than "0"

  Scenario: Search orders by date range
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/q/order" with payload
    """
    {
      "numRowsInPage": 10,
      "pageNum": 1,
      "filters": {
        "tenant_id": "tenant0",
        "created_time_from": "2024-01-01T00:00:00",
        "created_time_to": "2024-12-31T23:59:59"
      }
    }
    """
    Then the http status code is 200
    And success is true

  Scenario: Cancel order
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I PUT a REST request to URL "/api/orders/order-001/cancel"
    Then the http status code is 200
    And success is true
    And the REST response key "status" is "CANCELLED"

  Scenario: Get order items
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/orders/order-001/items"
    Then the http status code is 200
    And success is true
    And the REST response is an array
