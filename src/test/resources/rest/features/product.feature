Feature: Product Management API Tests

  Scenario: Create a new product
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/api/products" with payload
    """
    {
      "name": "Wireless Mouse",
      "description": "Ergonomic wireless mouse with USB receiver",
      "categoryId": "category-001",
      "featureDescription": "2.4GHz wireless connection, 1600 DPI",
      "salesDescription": "Best selling wireless mouse",
      "detailsDescription": "Compatible with Windows and Mac",
      "tags": ["electronics", "accessories", "wireless"]
    }
    """
    Then the http status code is 201
    And success is true
    And the REST response key "name" is "Wireless Mouse"
    And the REST response key "categoryId" is "category-001"

  Scenario: Get product by ID
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/products/product-001"
    Then the http status code is 200
    And success is true
    And the REST response key "id" is "product-001"
    And the REST response key "name" exists

  Scenario: Update product
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I PUT a REST request to URL "/api/products/product-001" with payload
    """
    {
      "name": "Updated Product Name",
      "description": "Updated description",
      "categoryId": "category-001"
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "name" is "Updated Product Name"

  Scenario: Search products by category
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/q/product" with payload
    """
    {
      "numRowsInPage": 10,
      "pageNum": 1,
      "filters": {
        "tenant_id": "default",
        "category_id": "category-001"
      }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "numRowsReturned" is greater than "0"

  Scenario: Search products with tags
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/q/product" with payload
    """
    {
      "numRowsInPage": 10,
      "pageNum": 1,
      "filters": {
        "tenant_id": "default"
      }
    }
    """
    Then the http status code is 200
    And success is true

  Scenario: Delete product
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I DELETE a REST request to URL "/api/products/product-test-001"
    Then the http status code is 204

  Scenario: Get non-existent product returns 404
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/products/non-existent-id"
    Then the http status code is 404
