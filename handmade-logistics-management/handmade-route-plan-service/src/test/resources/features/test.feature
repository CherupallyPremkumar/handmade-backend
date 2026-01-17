Feature: Testcase ID 
Tests the routeplan Workflow Service using a REST client. Routeplan service exists and is under test.
It helps to create a routeplan and manages the state of the routeplan as documented in states xml
Scenario: Create a new routeplan
Given that "flowName" equals "routePlanFlow"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/routeplan" with payload
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

Scenario: Retrieve the routeplan that just got created
When I GET a REST request to URL "/routeplan/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the assign event to the routeplan with comments
Given that "comment" equals "Comment for assign"
And that "event" equals "assign"
When I PATCH a REST request to URL "/routeplan/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ASSIGNED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the start event to the routeplan with comments
Given that "comment" equals "Comment for start"
And that "event" equals "start"
When I PATCH a REST request to URL "/routeplan/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_PROGRESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the complete event to the routeplan with comments
Given that "comment" equals "Comment for complete"
And that "event" equals "complete"
When I PATCH a REST request to URL "/routeplan/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "COMPLETED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
