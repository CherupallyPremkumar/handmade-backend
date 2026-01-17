Feature: Tests the experimentDefinition Workflow Service using a REST client. This is done only for the
first testcase. Experiment service exists and is under test.
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

 Scenario: Send the complete event to the experimentDefinition with comments
 Given that "comment" equals "Comment for complete"
 And that "event" equals "complete"
When I PATCH a REST request to URL "/experimentDefinition/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "COMPLETED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to experimentDefinition . This will err out.
When I PATCH a REST request to URL "/experimentDefinition/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

