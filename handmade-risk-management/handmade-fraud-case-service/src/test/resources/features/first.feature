Feature: Tests the fraudcase Workflow Service using a REST client. This is done only for the
first testcase. Fraudcase service exists and is under test.
It helps to create a fraudcase and manages the state of the fraudcase as documented in states xml
Scenario: Create a new fraudcase
Given that "flowName" equals "fraudCaseFlow"
And that "initialState" equals "REPORTED"
When I POST a REST request to URL "/fraudcase" with payload
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

Scenario: Retrieve the fraudcase that just got created
When I GET a REST request to URL "/fraudcase/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the assign event to the fraudcase with comments
 Given that "comment" equals "Comment for assign"
 And that "event" equals "assign"
When I PATCH a REST request to URL "/fraudcase/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ASSIGNED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the investigate event to the fraudcase with comments
 Given that "comment" equals "Comment for investigate"
 And that "event" equals "investigate"
When I PATCH a REST request to URL "/fraudcase/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "INVESTIGATING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the resolve event to the fraudcase with comments
 Given that "comment" equals "Comment for resolve"
 And that "event" equals "resolve"
When I PATCH a REST request to URL "/fraudcase/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RESOLVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the close event to the fraudcase with comments
 Given that "comment" equals "Comment for close"
 And that "event" equals "close"
When I PATCH a REST request to URL "/fraudcase/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CLOSED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to fraudcase . This will err out.
When I PATCH a REST request to URL "/fraudcase/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

