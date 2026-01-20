Feature: Tests the comparisonItem Service using a REST client.
 
  Scenario: Save the comparisonItem first.
    Given that "tenant" equals "tenant0"
    And that "employee" equals "E1"
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I construct a REST request with header "x-chenile-eid" and value "${employee}"
    And I POST a REST request to URL "/comparisonItem" with payload
    """json
    {
      "comparisonId": "comp-001",
      "productId": "prod-101",
      "displayOrder": 1
    }
    """
    Then success is true
    And store "$.payload.id" from response to "id"

  Scenario: Retrieve the saved comparisonItem
    Given that "entity" equals "comparisonItem"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I GET a REST request to URL "/${entity}/${id}"
    Then success is true
    And the REST response key "id" is "${id}"
    And the REST response key "comparisonId" is "comp-001"
    And the REST response key "productId" is "prod-101"

  Scenario: Save a comparisonItem using an ID that already is determined
    Given that "customId" equals "comp-item-123"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I construct a REST request with header "x-chenile-eid" and value "E1"
    And I POST a REST request to URL "/comparisonItem" with payload
    """json
    {
      "id": "${customId}",
      "comparisonId": "comp-002",
      "productId": "prod-202",
      "displayOrder": 2
    }
    """
    Then success is true
    And the REST response key "id" is "${customId}"

  Scenario: Retrieve the saved comparisonItem with custom ID
    Given that "entity" equals "comparisonItem"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I GET a REST request to URL "/${entity}/comp-item-123"
    Then success is true
    And the REST response key "id" is "comp-item-123"
    And the REST response key "comparisonId" is "comp-002"