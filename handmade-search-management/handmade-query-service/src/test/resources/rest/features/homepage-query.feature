Feature: Homepage Search Query Service
  As a customer
  I want to see curated product sections on the homepage
  So that I can discover relevant products, deals, and best sellers

  Scenario: Search Daily Deals
    When I POST a REST request to URL "/q/search-daily-deals" with payload
    """
    {
    "pageNum": 1,
    "numRowsInPage": 10
    }
    """
    Then the http status code is 200
    And the top level code is 200
    And success is true
    And the REST response key "list[0].row.sellerName" is "Organic Soap Creations"
    And the REST response contains key "list[0].row.imageUrl"

  Scenario: Search Best Sellers
    When I POST a REST request to URL "/q/search-best-sellers" with payload
    """
    {
    "pageNum": 1,
    "numRowsInPage": 10
    }
    """
    Then the http status code is 200
    And the top level code is 200
    And success is true
    And the REST response key "list[0].row.sellerName" is "Handcrafted Pottery Studio"
    And the REST response key "list[0].row.averageRating" is "5.0"

  Scenario: Search New Arrivals
    When I POST a REST request to URL "/q/search-new-arrivals" with payload
    """
    {
    "pageNum": 1,
    "numRowsInPage": 10
    }
    """
    Then the http status code is 200
    And the top level code is 200
    And success is true
    And the REST response key "list[0].row.sellerName" is "Organic Soap Creations"

  Scenario: Search Recommendations
    When I POST a REST request to URL "/q/search-recommendations" with payload
    """
    {
    "sourceProductId": "prod-001",
    "pageNum": 1,
    "numRowsInPage": 10
    }
    """
    Then the http status code is 200
    And the top level code is 200
    And success is true
    And the REST response key "list[0].row.sellerName" is "Organic Soap Creations"

  Scenario: Search Sponsored Products
    When I POST a REST request to URL "/q/search-sponsored-products" with payload
    """
    {
    "pageNum": 1,
    "numRowsInPage": 10
    }
    """
    Then the http status code is 200
    And the top level code is 200
    And success is true
    And the REST response key "list[0].row.sellerName" is "Handcrafted Pottery Studio"
