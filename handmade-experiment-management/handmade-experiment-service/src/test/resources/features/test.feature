Feature: Testcase ID 
Tests the experimentDefinition Workflow Service using a REST client. Experiment service exists and is under test.
It helps to create a experimentDefinition and manages the state of the experimentDefinition as documented in states xml
Scenario: Create a new experimentDefinition
Given that "flowName" equals "experimentDefinitionFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/experimentDefinition" with payload
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

Scenario: Retrieve the experimentDefinition that just got created
When I GET a REST request to URL "/experimentDefinition/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the activate event to the experimentDefinition with comments
Given that "comment" equals "Comment for activate"
And that "event" equals "activate"
When I PATCH a REST request to URL "/experimentDefinition/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the pause event to the experimentDefinition with comments
Given that "comment" equals "Comment for pause"
And that "event" equals "pause"
When I PATCH a REST request to URL "/experimentDefinition/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PAUSED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
