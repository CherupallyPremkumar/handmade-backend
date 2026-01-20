Feature: Testcase ID
  Tests the dispute Workflow Service using a REST client. Dispute service exists and is under test.
  It helps to create a dispute and manages the state of the dispute as documented in states xml

  Scenario: Create a new dispute
    Given that "flowName" equals "DISPUTE_FLOW"
    And that "initialState" equals "OPEN"
    When I POST a REST request to URL "/dispute" with payload
"""json
{
    "disputeNumber": "DISP-2026-002",
    "orderId": "ORD-666",
    "customerId": "CUST-888",
    "sellerId": "SELL-111",
    "disputeType": "NOT_AS_DESCRIBED",
    "disputeReason": "Item color is completely different from pictures",
    "disputeAmount": 45.50,
    "openedAt": "2026-01-19T12:00:00Z"
}
"""
    Then success is true
    And the REST response contains key "mutatedEntity"
    And store "$.payload.mutatedEntity.id" from response to "id"
    And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
    And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
    And the REST response key "mutatedEntity.disputeNumber" is "DISP-2026-002"

  Scenario: Retrieve the dispute that just got created
    When I GET a REST request to URL "/dispute/${id}"
    Then success is true
    And the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.id" is "${id}"
    And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

  Scenario: Send the close event to the dispute with comments
    Given that "comment" equals "Closed as buyer withdrew the dispute"
    And that "event" equals "close"
    When I PATCH a REST request to URL "/dispute/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
    Then success is true
    And the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.id" is "${id}"
    And the REST response key "mutatedEntity.currentState.stateId" is "CLOSED_NO_ACTION"
    And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
