Feature: Advanced Search & Personalization Features

  Scenario: Get Rating Facets for Home & Garden
    When I POST a REST request to URL "/q/rating-facets" with payload
    """
    {
      "filters": {
        "classification_id": "cat-002"
      }
    }
    """
    Then the http status code is 200
    And the top level code is 200
    And success is true
    And the REST response key "list[0].row.facet_value" is "1★ & above"
    And the REST response key "payload.list[0].row.facet_count" is 1

  Scenario: Get Full Product Details
    When I POST a REST request to URL "/q/get-product-details" with payload
    """
    {
      "filters": {
        "id": "PROD-001"
      }
    }
    """
    Then the http status code is 200
    And the top level code is 200
    And success is true
    And the REST response key "list[0].row.name" is "Artisan Hand-Thrown Turquoise Mug"
    And the REST response key "list[0].row.price" is "25.0"

  Scenario: Get Product Variants
    When I POST a REST request to URL "/q/get-product-variants" with payload
    """
    {
      "filters": {
        "id": "PROD-SEARCH-003"
      }
    }
    """
    Then the http status code is 200
    And the top level code is 200
    And success is true
    And the REST response contains key "list"

  Scenario: Search Recently Viewed Items
    When I POST a REST request to URL "/q/search-recently-viewed" with payload
    """
    {
      "filters": {
        "user_id": "CUST-001"
      }
    }
    """
    Then the http status code is 200
    And the top level code is 200
    And success is true
    And the REST response key "list[0].row.name" is "Hand-Woven Organic Silk Scarf"

  Scenario: Search Buy It Again Items
    When I POST a REST request to URL "/q/search-buy-it-again" with payload
    """
    {
      "filters": {
        "user_id": "cust-001"
      }
    }
    """
    Then the http status code is 200
    And the top level code is 200
    And success is true
    And the REST response contains key "list"
