Feature: Comprehensive Search & Personalization Scenarios

  Scenario: Exact Keyword Match
    When I POST a REST request to URL "/q/search-products" with payload
    """
    {
      "filters": { "name": "Artisan Hand-Thrown Turquoise Mug" }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "list[0].row.name" is "Artisan Hand-Thrown Turquoise Mug"

  Scenario: Partial Keyword Match
    When I POST a REST request to URL "/q/search-products" with payload
    """
    {
      "filters": { "name": "Artisan" }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "list[0].row.name" contains string "Artisan"

  Scenario: Search with Non-existent Keyword
    When I POST a REST request to URL "/q/search-products" with payload
    """
    {
      "filters": { "name": "NonExistentProductXYZ" }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "payload.list" has 0 records

  Scenario: Filter by Category and Price Range
    When I POST a REST request to URL "/q/search-products" with payload
    """
    {
      "filters": {
        "classification_id": "cat-002",
        "minPrice": 10,
        "maxPrice": 30
      }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "list[0].row.id" is "PROD-001"

  Scenario: Multi-select Brand Filter
    When I POST a REST request to URL "/q/search-products" with payload
    """
    {
      "filters": {
        "brand": ["Pottery Studio", "Organic Soaps"]
      }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response contains key "list"

  Scenario: PDP for Standalone Product
    When I POST a REST request to URL "/q/get-product-variants" with payload
    """
    {
      "filters": { "id": "PROD-STANDALONE-001" }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "payload.list" has 1 records

  Scenario: PDP for Variant Product
    When I POST a REST request to URL "/q/get-product-variants" with payload
    """
    {
      "filters": { "id": "PROD-SEARCH-003" }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "payload.list" has 2 records

  Scenario: PDP for Invalid Product ID
    When I POST a REST request to URL "/q/get-product-details" with payload
    """
    {
      "filters": { "id": "invalid-id" }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "payload.list" has 0 records

  Scenario: Recently Viewed for New User (No History)
    When I POST a REST request to URL "/q/search-recently-viewed" with payload
    """
    {
      "filters": { "user_id": "new-user-123" }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "payload.list" has 0 records

  Scenario: Buy It Again for New User (No Orders)
    When I POST a REST request to URL "/q/search-buy-it-again" with payload
    """
    {
      "filters": { "user_id": "new-user-123" }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "payload.list" has 0 records

  Scenario: Filter by Category with No Products
    When I POST a REST request to URL "/q/search-products" with payload
    """
    {
      "filters": { "classification_id": "empty-cat" }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "payload.list" has 0 records

  Scenario: Search with Pagination (Page Beyond Results snaps to Last Page)
    # Chenile's AbstractSearchServiceImpl snaps the page to maxPages if pageNum > maxPages
    When I POST a REST request to URL "/q/search-products" with payload
    """
    {
      "filters": { "name": "Ceramic" },
      "pageNum": 5,
      "numRowsInPage": 10
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "payload.list" has 1 records

  Scenario: Search with Empty Filters (Return All)
    When I POST a REST request to URL "/q/search-products" with payload
    """
    {
      "filters": {}
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response contains key "list"

  Scenario: Sort Products by Name Ascending
    When I POST a REST request to URL "/q/search-products" with payload
    """
    {
      "filters": {},
      "sortCriteria": [
        { "name": "name", "ascendingOrder": true }
      ]
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "list[0].row.name" contains string "Artisan"

  Scenario: Sort Products by Name Descending
    When I POST a REST request to URL "/q/search-products" with payload
    """
    {
      "filters": {},
      "sortCriteria": [
        { "name": "name", "ascendingOrder": false }
      ]
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "list[0].row.name" contains string "Sandalwood"
