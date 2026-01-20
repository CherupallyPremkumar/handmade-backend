Feature: Tests the dispute Workflow Service using a REST client. This is done only for the
  first testcase. Dispute service exists and is under test.
  It helps to create a dispute and manages the state of the dispute as documented in states xml

  Scenario: Create a new dispute
    Given that "flowName" equals "DISPUTE_FLOW"
    And that "initialState" equals "OPEN"
    When I POST a REST request to URL "/dispute" with payload
"""json
{
    "disputeNumber": "DISP-2026-001",
    "orderId": "ORD-555",
    "customerId": "CUST-999",
    "sellerId": "SELL-777",
    "disputeType": "ITEM_NOT_RECEIVED",
    "disputeReason": "Package never arrived despite delivery confirmation",
    "disputeAmount": 150.00,
    "openedAt": "2026-01-19T10:00:00Z"
}
"""
    Then success is true
    And the REST response contains key "mutatedEntity"
    And store "$.payload.mutatedEntity.id" from response to "id"
    And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
    And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
    And the REST response key "mutatedEntity.disputeNumber" is "DISP-2026-001"

  Scenario: Retrieve the dispute that just got created
    When I GET a REST request to URL "/dispute/${id}"
    Then success is true
    And the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.id" is "${id}"
    And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

  Scenario: Send the resolve event to the dispute with comments
    Given that "comment" equals "Buyer refunded after investigation"
    And that "event" equals "resolve"
    When I PATCH a REST request to URL "/dispute/${id}/${event}" with payload
"""json
{
    "comment": "${comment}",
    "resolutionType": "REFUNDED",
    "resolvedAmount": 150.00,
    "winner": "BUYER"
}
"""
    Then success is true
    And the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.id" is "${id}"
    And the REST response key "mutatedEntity.currentState.stateId" is "RESOLVED"
    And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"


  Scenario: Send an invalid event to dispute . This will err out.
    When I PATCH a REST request to URL "/dispute/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
    Then the REST response does not contain key "mutatedEntity"
    And the http status code is 422
