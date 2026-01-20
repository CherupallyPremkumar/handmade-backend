Feature: Platform Admin Dashboard Queries

  As a Marketplace Administrator
  I want to view marketplace health metrics
  To monitor the overall state of the platform

  Scenario: View Unified Marketplace Stats
    When I POST a REST request to URL "/q/admin-marketplace-stats" with payload
    """
    {}
    """
    Then the http status code is 200
    And success is true
    And the REST response contains key "list"
    # Verify presence of different metric types
    And the REST response key "payload.list[?(@.row.metric_type == 'SELLERS')]" has 2 records
    And the REST response key "payload.list[?(@.row.metric_type == 'PRODUCTS')]" has 2 records


  Scenario: View Category Performance Health
    When I POST a REST request to URL "/q/admin-category-health" with payload
    """
    {}
    """
    Then the http status code is 200
    And success is true
    # cat-002 (Pottery) should have 2 products (prod-001, standalone-001)
    And the REST response key "payload.list[?(@.row.node_id == 'cat-002')].row.product_count" is 2
    # cat-001 (Home & Garden) should have 2 products (prod-002, prod-003)
    And the REST response key "payload.list[?(@.row.node_id == 'cat-001')].row.product_count" is 2
