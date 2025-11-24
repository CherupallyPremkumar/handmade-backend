Feature: Testcase ID 2
Tests the tenant Workflow Service using a REST client. Tenant service exists and is under test.
It helps to create a tenant and manages the state of the tenant as documented in states xml

Scenario: Create a new tenant
Given that "flowName" equals "tenantFlow"
And that "initialState" equals "CREATED"
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I POST a REST request to URL "/tenant" with payload
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

Scenario: Retrieve the tenant that just got created
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I GET a REST request to URL "/tenant/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"


Scenario: Send the suspend event to the tenant with comments
Given that "comment" equals "Comment for suspend"
And that "event" equals "suspend"
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I PATCH a REST request to URL "/tenant/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SUSPENDED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send the delete event to the tenant with comments
Given that "comment" equals "Comment for delete"
And that "event" equals "delete"
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I PATCH a REST request to URL "/tenant/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DELETED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
