Feature: Tests the auditprocess Workflow Service using a REST client. This is done only for the
first testcase. Auditprocess service exists and is under test.
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

 Scenario: Send the fail event to the auditprocess with comments
 Given that "comment" equals "Comment for fail"
 And that "event" equals "fail"
When I PATCH a REST request to URL "/auditprocess/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FAILED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to auditprocess . This will err out.
When I PATCH a REST request to URL "/auditprocess/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

