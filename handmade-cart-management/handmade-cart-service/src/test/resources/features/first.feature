Feature: Tests the cart Workflow Service using a REST client. This is done only for the
  first testcase. Cart service exists and is under test.
  It helps to create a cart and manages the state of the cart as documented in states xml

  Scenario: Create a new cart
    Given that "flowName" equals "cartFlow"
    And that "initialState" equals "ACTIVE"
    When I POST a REST request to URL "/cart" with payload
"""json
{
    "description": "Description"
}
"""
    Then the REST response contains key "mutatedEntity"
    And store "$.payload.mutatedEntity.id" from response to "id"
    And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
    And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
    And the REST response key "mutatedEntity.description" is "Description"

  Scenario: Retrieve the cart that just got created
    When I GET a REST request to URL "/cart/${id}"
    Then the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.id" is "${id}"
    And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

  Scenario: Send the abandon event to the cart with comments
    Given that "comment" equals "Comment for abandon"
    And that "event" equals "abandon"
    When I PATCH a REST request to URL "/cart/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
    Then the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.id" is "${id}"
    And the REST response key "mutatedEntity.currentState.stateId" is "ABANDONED"
    And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"


  Scenario: Send an invalid event to cart . This will err out.
    When I PATCH a REST request to URL "/cart/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
    Then the REST response does not contain key "mutatedEntity"
    And the http status code is 422

