Feature: Testcase ID 
Tests the auditprocess Workflow Service using a REST client. Auditprocess service exists and is under test.
It helps to create a auditprocess and manages the state of the auditprocess as documented in states xml
Scenario: Create a new auditprocess
Given that "flowName" equals "auditProcessFlow"
And that "initialState" equals "INITIATED"
When I POST a REST request to URL "/auditprocess" with payload
"""json
{
    "auditType": "COMPLIANCE",
    "entityType": "SELLER",
    "entityId": "seller-001",
    "priority": "HIGH"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.auditType" is "COMPLIANCE"

Scenario: Retrieve the auditprocess that just got created
When I GET a REST request to URL "/auditprocess/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the start event to the auditprocess with comments
Given that "comment" equals "Comment for start"
And that "event" equals "start"
When I PATCH a REST request to URL "/auditprocess/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_PROGRESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the complete event to the auditprocess with comments
Given that "comment" equals "Comment for complete"
And that "event" equals "complete"
When I PATCH a REST request to URL "/auditprocess/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "COMPLETED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
