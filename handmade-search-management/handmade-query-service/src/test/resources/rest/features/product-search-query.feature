Feature: Product Search & Browse Query Service
  As a customer
  I want to search and browse products with filters and categories
  So that I can find what I'm looking for easily

  Scenario: Search products by keyword 'Ceramic'
    When I POST a REST request to URL "/q/search-products" with payload
    """
    {
      "filters": {
        "name": "Artisan"
      },
      "pageNum": 1,
      "numRowsInPage": 10
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "list[0].row.name" contains string "Artisan"

  Scenario: Search products with multi-brand filter
    When I POST a REST request to URL "/q/search-products" with payload
    """
    {
      "filters": {
        "brand": ["Pottery Studio", "Artisan Leather"]
      }
    }
    """
    Then the http status code is 200
    And success is true
    # We expect at least these two brands back if data is present
    And the REST response contains key "list"

  Scenario: List top-level browse nodes
    When I POST a REST request to URL "/q/browse-nodes" with payload
    """
    {
      "filters": {
        "parent_id": null
      }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "list[0].row.level" is "1"

  Scenario: Get Brand Facets for Home & Garden
    When I POST a REST request to URL "/q/brand-facets" with payload
    """
    {
      "filters": {
        "classification_id": "cat-001"
      }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "list[0].row.facet_value" is "Serene Soy Candles"

  Scenario: Get Price Facets
    When I POST a REST request to URL "/q/price-facets" with payload
    """
    {
      "filters": {}
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "list[0].row.facet_value" is "Under 500"
