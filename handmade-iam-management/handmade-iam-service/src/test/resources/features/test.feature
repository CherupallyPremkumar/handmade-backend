Feature: Testcase ID 
Tests the iam Workflow Service using a REST client. Iam service exists and is under test.
It helps to create a iam and manages the state of the iam as documented in states xml
Scenario: Create a new iam
Given that "flowName" equals "ROLE_FLOW"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/iam" with payload
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

Scenario: Retrieve the iam that just got created
When I GET a REST request to URL "/iam/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the activateRole event to the iam with comments
Given that "comment" equals "Comment for activateRole"
And that "event" equals "activateRole"
When I PATCH a REST request to URL "/iam/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the deactivateRole event to the iam with comments
Given that "comment" equals "Comment for deactivateRole"
And that "event" equals "deactivateRole"
When I PATCH a REST request to URL "/iam/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DEACTIVATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the iam Workflow Service using a REST client. Iam service exists and is under test.
It helps to create a iam and manages the state of the iam as documented in states xml
Scenario: Create a new iam
Given that "flowName" equals "ROLE_FLOW"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/iam" with payload
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

Scenario: Retrieve the iam that just got created
When I GET a REST request to URL "/iam/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the deactivateRole event to the iam with comments
Given that "comment" equals "Comment for deactivateRole"
And that "event" equals "deactivateRole"
When I PATCH a REST request to URL "/iam/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DEACTIVATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
