Feature: Testcase ID 
Tests the permission Workflow Service using a REST client. Permission service exists and is under test.
It helps to create a permission and manages the state of the permission as documented in states xml

Scenario: Create a new permission
Given that "flowName" equals "permissionFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/permission" with payload
"""json
{
    "name": "MANAGE_ORDERS",
    "module": "ORDER_MANAGEMENT"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.name" is "MANAGE_ORDERS"

Scenario: Retrieve the permission that just got created
When I GET a REST request to URL "/permission/${id}"
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the activate event to the permission
Given that "event" equals "activate"
When I PATCH a REST request to URL "/permission/${id}/${event}" with payload
"""json
{
    "comment": "Activating permission for production use"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"

Scenario: Send the deprecate event to the permission
Given that "event" equals "deprecate"
When I PATCH a REST request to URL "/permission/${id}/${event}" with payload
"""json
{
    "comment": "Deprecating old permission"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "DEPRECATED"
