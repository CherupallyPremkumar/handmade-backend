Feature: Category Management API Tests

  Scenario: Create a new category
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/api/categories" with payload
    """
    {
      "name": "Sports & Outdoors",
      "slug": "sports-outdoors",
      "description": "Sports equipment and outdoor gear"
    }
    """
    Then the http status code is 201
    And success is true
    And the REST response key "name" is "Sports & Outdoors"
    And the REST response key "slug" is "sports-outdoors"
    And the REST response key "productCount" is "0"

  Scenario: Get all categories
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/categories"
    Then the http status code is 200
    And success is true
    And the REST response is an array
    And the REST response array size is greater than "0"

  Scenario: Get category by ID
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/categories/category-001"
    Then the http status code is 200
    And success is true
    And the REST response key "id" is "category-001"
    And the REST response key "name" is "Electronics"

  Scenario: Get category by slug
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/categories/slug/electronics"
    Then the http status code is 200
    And success is true
    And the REST response key "slug" is "electronics"

  Scenario: Update category
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I PUT a REST request to URL "/api/categories/category-001" with payload
    """
    {
      "name": "Electronics & Gadgets",
      "description": "Updated description for electronics"
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "name" is "Electronics & Gadgets"

  Scenario: Get products in category
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/categories/category-001/products"
    Then the http status code is 200
    And success is true
    And the REST response is an array

  Scenario: Delete category
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I DELETE a REST request to URL "/api/categories/category-test-001"
    Then the http status code is 204

  Scenario: Query categories
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/q/category" with payload
    """
    {
      "numRowsInPage": 10,
      "pageNum": 1,
      "filters": {
        "tenant_id": "tenant0"
      }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "numRowsReturned" is greater than "0"
