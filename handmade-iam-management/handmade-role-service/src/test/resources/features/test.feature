Feature: Testcase ID 
Tests the role Workflow Service using a REST client. Role service exists and is under test.
It helps to create a role and manages the state of the role as documented in states xml

Scenario: Create a new role
Given that "flowName" equals "roleFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/role" with payload
"""json
{
    "name": "CATALOG_MANAGER",
    "description": "Manages products and categories"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.name" is "CATALOG_MANAGER"

Scenario: Retrieve the role that just got created
When I GET a REST request to URL "/role/${id}"
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the activate event to the role
Given that "event" equals "activate"
When I PATCH a REST request to URL "/role/${id}/${event}" with payload
"""json
{
    "comment": "Activating role for catalog team"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"

Scenario: Send the deprecate event to the role
Given that "event" equals "deprecate"
When I PATCH a REST request to URL "/role/${id}/${event}" with payload
"""json
{
    "comment": "Deprecating role due to organizational change"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "DEPRECATED"
