Feature: Testcase ID 
Tests the support Workflow Service using a REST client. Support service exists and is under test.
It helps to create a support and manages the state of the support as documented in states xml
Scenario: Create a new support
Given that "flowName" equals "supportCaseFlow"
And that "initialState" equals "NEW"
When I POST a REST request to URL "/support" with payload
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

Scenario: Retrieve the support that just got created
When I GET a REST request to URL "/support/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the assign event to the support with comments
Given that "comment" equals "Comment for assign"
And that "event" equals "assign"
When I PATCH a REST request to URL "/support/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_PROGRESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the resolve event to the support with comments
Given that "comment" equals "Comment for resolve"
And that "event" equals "resolve"
When I PATCH a REST request to URL "/support/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RESOLVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
