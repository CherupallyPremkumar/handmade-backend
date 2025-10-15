Feature: Wishlist Management API Tests

  Scenario: Create a new wishlist
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/api/wishlists" with payload
    """
    {
      "customerId": "customer-001",
      "name": "My Favorites",
      "description": "Products I want to buy"
    }
    """
    Then the http status code is 201
    And success is true
    And the REST response key "customerId" is "customer-001"
    And the REST response key "name" is "My Favorites"

  Scenario: Get customer wishlists
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/wishlists/customer/customer-001"
    Then the http status code is 200
    And success is true
    And the REST response is an array

  Scenario: Get wishlist by ID
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/wishlists/wishlist-001"
    Then the http status code is 200
    And success is true
    And the REST response key "id" is "wishlist-001"

  Scenario: Add item to wishlist
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/api/wishlists/wishlist-001/items" with payload
    """
    {
      "productVariantId": "variant-001",
      "notes": "Want this for birthday"
    }
    """
    Then the http status code is 201
    And success is true
    And the REST response key "productVariantId" is "variant-001"

  Scenario: Get wishlist items
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/wishlists/wishlist-001/items"
    Then the http status code is 200
    And success is true
    And the REST response is an array

  Scenario: Update wishlist item notes
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I PUT a REST request to URL "/api/wishlists/wishlist-001/items/wishlist-item-001" with payload
    """
    {
      "notes": "Updated notes"
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "notes" is "Updated notes"

  Scenario: Remove item from wishlist
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I DELETE a REST request to URL "/api/wishlists/wishlist-001/items/wishlist-item-001"
    Then the http status code is 204

  Scenario: Move wishlist item to cart
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/api/wishlists/wishlist-001/items/wishlist-item-001/move-to-cart"
    Then the http status code is 200
    And success is true

  Scenario: Delete wishlist
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I DELETE a REST request to URL "/api/wishlists/wishlist-test-001"
    Then the http status code is 204

  Scenario: Query wishlists
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/q/wishlist" with payload
    """
    {
      "numRowsInPage": 10,
      "pageNum": 1,
      "filters": {
        "tenant_id": "tenant0",
        "customer_id": "customer-001"
      }
    }
    """
    Then the http status code is 200
    And success is true
