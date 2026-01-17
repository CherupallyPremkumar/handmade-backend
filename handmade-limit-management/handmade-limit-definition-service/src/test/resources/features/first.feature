Feature: Tests the limitdefinition Workflow Service using a REST client. This is done only for the
first testcase. Limitdefinition service exists and is under test.
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

 Scenario: Send the suspend event to the limitdefinition with comments
 Given that "comment" equals "Comment for suspend"
 And that "event" equals "suspend"
When I PATCH a REST request to URL "/limitdefinition/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SUSPENDED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to limitdefinition . This will err out.
When I PATCH a REST request to URL "/limitdefinition/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

