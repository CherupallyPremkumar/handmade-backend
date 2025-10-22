Feature: Tests the Cart Query Service using a REST client.

Given that "tenant" equals "tenant0"
  Scenario: Test create cart
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I construct a REST request with header "chenile-entry-point" and value "HTTP"
    And I construct a REST request with header "x-chenile-uid" and value "1"

    And I POST a REST request to URL "/cart" with payload
    """
    {

    }
    """
    Then the REST response contains key "mutatedEntity"
    And store "payload.mutatedEntity.id" from response to "id"
    And the REST response key "mutatedEntity.id" is "${id}"
    And the REST response key "mutatedEntity.currentState.stateId" is "EMPTY"

  Scenario: Test create cartItem
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I construct a REST request with header "chenile-entry-point" and value "HTTP"
    And I construct a REST request with header "chenile-sub-tenant-id" and value "tenant-001"
    And I POST a REST request to URL "/cartItem" with payload
    """
    {
     "productVariantId":"variant-101",
     "subTenantId":"tenant-001",
     "cartId": "${id}",
     "quantity": 1
    }
    """
    Then the REST response contains key "mutatedEntity"
    And store "payload.mutatedEntity.id" from response to "id"
    And the REST response key "mutatedEntity.id" is "${id}"
    And the REST response key "mutatedEntity.currentState.stateId" is "EMPTY"