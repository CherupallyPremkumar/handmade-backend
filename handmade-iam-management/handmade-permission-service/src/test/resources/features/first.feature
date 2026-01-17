Feature: Tests the permission Workflow Service using a REST client. This is done only for the
first testcase. Permission service exists and is under test.
It helps to create a permission and manages the state of the permission as documented in states xml
Scenario: Create a new permission
Given that "flowName" equals "permissionFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/permission" with payload
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

Scenario: Retrieve the permission that just got created
When I GET a REST request to URL "/permission/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the activate event to the permission with comments
 Given that "comment" equals "Comment for activate"
 And that "event" equals "activate"
When I PATCH a REST request to URL "/permission/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the deprecate event to the permission with comments
 Given that "comment" equals "Comment for deprecate"
 And that "event" equals "deprecate"
When I PATCH a REST request to URL "/permission/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DEPRECATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to permission . This will err out.
When I PATCH a REST request to URL "/permission/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

