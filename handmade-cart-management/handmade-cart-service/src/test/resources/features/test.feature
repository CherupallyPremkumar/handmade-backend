Feature: Testcase ID
  Tests the cart Workflow Service using a REST client. Cart service exists and is under test.
  It helps to create a cart and manages the state of the cart as documented in states xml

  Scenario: Create a new cart
    Given that "flowName" equals "cartFlow"
    And that "initialState" equals "ACTIVE"
    When I POST a REST request to URL "/cart" with payload
"""json
{
    "customerId": "cust-002",
    "sessionId": "sess-002"
}
"""
    Then the REST response contains key "mutatedEntity"
    And store "$.payload.mutatedEntity.id" from response to "id"
    And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
    And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
    And the REST response key "mutatedEntity.customerId" is "cust-002"

  Scenario: Retrieve the cart that just got created
    When I GET a REST request to URL "/cart/${id}"
    Then the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.id" is "${id}"
    And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

  Scenario: Send the merge event to the cart with comments
    Given that "comment" equals "Comment for merge"
    And that "event" equals "merge"
    When I PATCH a REST request to URL "/cart/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
    Then the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.id" is "${id}"
    And the REST response key "mutatedEntity.currentState.stateId" is "MERGED"
    And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
