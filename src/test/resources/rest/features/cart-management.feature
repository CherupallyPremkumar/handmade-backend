Feature: Cart Management API Tests

  Background:
    Given I construct a REST request with authorization header in realm "default" for user "customer-001" and password "password123"
    And I construct a REST request with header "x-chenile-tenant-id" and value "default"

  Scenario: Create a new cart for customer
    When I POST a REST request to URL "/api/carts" with payload
    """
    {
      "customerId": "customer-001"
    }
    """
    Then the http status code is 201
    And success is true
    And the REST response key "customerId" is "customer-001"
    And the REST response key "txn" is "0"

  Scenario: Get cart by customer ID
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/carts/customer/customer-001"
    Then the http status code is 200
    And success is true
    And the REST response key "customerId" is "customer-001"

  Scenario: Add item to cart
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/api/carts/cart-001/items" with payload
    """
    {
      "productVariantId": "variant-001",
      "quantity": 2
    }
    """
    Then the http status code is 201
    And success is true
    And the REST response key "productVariantId" is "variant-001"
    And the REST response key "quantity" is "2"

  Scenario: Update cart item quantity
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I PUT a REST request to URL "/api/carts/cart-001/items/cart-item-001" with payload
    """
    {
      "quantity": 5
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "quantity" is "5"

  Scenario: Remove item from cart
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I DELETE a REST request to URL "/api/carts/cart-001/items/cart-item-001"
    Then the http status code is 204

  Scenario: Get all cart items
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/carts/cart-001/items"
    Then the http status code is 200
    And success is true
    And the REST response is an array

  Scenario: Calculate cart totals
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/carts/cart-001/totals"
    Then the http status code is 200
    And success is true
    And the REST response key "totalAmount" exists
    And the REST response key "taxAmount" exists
    And the REST response key "discount" exists

  Scenario: Apply discount to cart
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I PUT a REST request to URL "/api/carts/cart-001/discount" with payload
    """
    {
      "discount": 25.00
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "discount" is "25.00"

  Scenario: Clear cart
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I DELETE a REST request to URL "/api/carts/cart-001/clear"
    Then the http status code is 204

  Scenario: Checkout cart
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/api/carts/cart-001/checkout" with payload
    """
    {
      "shippingAddress": "123 Main Street",
      "city": "New York",
      "state": "NY",
      "pincode": "10001",
      "paymentMethod": "CREDIT_CARD"
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "orderId" exists

  Scenario: Query cart by filters
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/q/cart" with payload
    """
    {
      "numRowsInPage": 10,
      "pageNum": 1,
      "filters": {
        "tenant_id": "default",
        "customer_id": "customer-001"
      }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "numRowsReturned" is greater than "0"
