Feature: Testcase ID 
  Tests the eventqueue Workflow Service using a REST client. Eventqueue service exists and is under test.
  It helps to create a eventqueue and manages the state of the eventqueue as documented in states xml

  Scenario: Create a new eventqueue
    Given that "flowName" equals "eventQueueFlow"
    And that "initialState" equals "NEW"
    When I POST a REST request to URL "/eventqueue" with payload
"""json
{
    "eventType": "PAYMENT_CONFIRMED",
    "payload": "{\"transactionId\": \"TRX-456\", \"status\": \"SUCCESS\"}"
}
"""
    Then success is true
    And the REST response contains key "mutatedEntity"
    And store "$.payload.mutatedEntity.id" from response to "id"
    And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
    And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
    And the REST response key "mutatedEntity.eventType" is "PAYMENT_CONFIRMED"

  Scenario: Retrieve the eventqueue that just got created
    When I GET a REST request to URL "/eventqueue/${id}"
    Then success is true
    And the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.id" is "${id}"
    And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

  Scenario: Send the process event to the eventqueue with comments
    Given that "comment" equals "Comment for process"
    And that "event" equals "process"
    When I PATCH a REST request to URL "/eventqueue/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
    Then success is true
    And the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.id" is "${id}"
    And the REST response key "mutatedEntity.currentState.stateId" is "PROCESSING"
    And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"

  Scenario: Send the complete event to the eventqueue with comments
    Given that "comment" equals "Comment for complete"
    And that "event" equals "complete"
    When I PATCH a REST request to URL "/eventqueue/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
    Then success is true
    And the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.id" is "${id}"
    And the REST response key "mutatedEntity.currentState.stateId" is "COMPLETED"
