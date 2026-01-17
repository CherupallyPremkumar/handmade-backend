Feature: Testcase ID 
Tests the limitdefinition Workflow Service using a REST client. Limitdefinition service exists and is under test.
It helps to create a limitdefinition and manages the state of the limitdefinition as documented in states xml
Scenario: Create a new limitdefinition
Given that "flowName" equals "limitDefinitionFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/limitdefinition" with payload
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

Scenario: Retrieve the limitdefinition that just got created
When I GET a REST request to URL "/limitdefinition/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the activate event to the limitdefinition with comments
Given that "comment" equals "Comment for activate"
And that "event" equals "activate"
When I PATCH a REST request to URL "/limitdefinition/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the deprecate event to the limitdefinition with comments
Given that "comment" equals "Comment for deprecate"
And that "event" equals "deprecate"
When I PATCH a REST request to URL "/limitdefinition/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DEPRECATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
