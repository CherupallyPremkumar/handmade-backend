Feature: Tests the role Workflow Service using a REST client. This is done only for the
first testcase. Role service exists and is under test.
It helps to create a role and manages the state of the role as documented in states xml
Scenario: Create a new role
Given that "flowName" equals "roleFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/role" with payload
"""json
{
    "name": "ADMIN_ROLE",
    "description": "Administrator Role"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.name" is "ADMIN_ROLE"

Scenario: Retrieve the role that just got created
When I GET a REST request to URL "/role/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the activate event to the role with comments
 Given that "comment" equals "Comment for activate"
 And that "event" equals "activate"
When I PATCH a REST request to URL "/role/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the deprecate event to the role with comments
 Given that "comment" equals "Comment for deprecate"
 And that "event" equals "deprecate"
When I PATCH a REST request to URL "/role/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DEPRECATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to role . This will err out.
When I PATCH a REST request to URL "/role/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

