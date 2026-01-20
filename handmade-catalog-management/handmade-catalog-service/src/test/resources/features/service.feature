Feature: Tests the catalogItem Service using a REST client.
 
  Scenario: Save the catalogItem first.
    Given that "tenant" equals "tenant0"
    And that "employee" equals "E1"
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I construct a REST request with header "x-chenile-eid" and value "${employee}"
    And I POST a REST request to URL "/catalogItem" with payload
    """json
    {
      "productId": "PROD-001"
    }
    """
    Then success is true
    And store "$.payload.id" from response to "id"

  Scenario: Retrieve the saved catalogItem
    Given that "entity" equals "catalogItem"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I GET a REST request to URL "/${entity}/${id}"
    Then success is true
    And the REST response key "id" is "${id}"
    And the REST response key "productId" is "PROD-001"

  Scenario: Save a catalogItem using an ID that already is determined
    Given that "customId" equals "cat-item-123"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I construct a REST request with header "x-chenile-eid" and value "E1"
    And I POST a REST request to URL "/catalogItem" with payload
    """json
    {
      "id": "${customId}",
      "productId": "PROD-002"
    }
    """
    Then success is true
    And the REST response key "id" is "${customId}"
    And the REST response key "productId" is "PROD-002"

  Scenario: Retrieve the saved catalogItem with custom ID
    Given that "entity" equals "catalogItem"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I GET a REST request to URL "/${entity}/${customId}"
    Then success is true
    And the REST response key "id" is "${customId}"
    And the REST response key "productId" is "PROD-002"