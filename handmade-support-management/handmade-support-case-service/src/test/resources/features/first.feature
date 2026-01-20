Feature: Tests the supportcase Workflow Service using a REST client. This is done only for the
first testcase. Supportcase service exists and is under test.
It helps to create a supportcase and manages the state of the supportcase as documented in states xml
Scenario: Create a new supportcase
Given that "flowName" equals "supportCaseFlow"
And that "initialState" equals "OPEN"
When I POST a REST request to URL "/supportcase" with payload
"""json
{
    "caseType": "TECHNICAL",
    "priority": "HIGH",
    "subject": "System is slow",
    "createdByUserId": "USER-001"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.subject" is "System is slow"

Scenario: Retrieve the supportcase that just got created
When I GET a REST request to URL "/supportcase/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the assign event to the supportcase with comments
 Given that "comment" equals "Comment for assign"
 And that "event" equals "assign"
When I PATCH a REST request to URL "/supportcase/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ASSIGNED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the investigate event to the supportcase with comments
 Given that "comment" equals "Comment for investigate"
 And that "event" equals "investigate"
When I PATCH a REST request to URL "/supportcase/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_PROGRESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the resolve event to the supportcase with comments
 Given that "comment" equals "Comment for resolve"
 And that "event" equals "resolve"
When I PATCH a REST request to URL "/supportcase/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RESOLVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to supportcase . This will err out.
When I PATCH a REST request to URL "/supportcase/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

