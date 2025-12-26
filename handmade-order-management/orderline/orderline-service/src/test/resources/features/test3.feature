Feature: Testcase ID 3
Tests the orderline Workflow Service using a REST client. Orderline service exists and is under test.
It helps to create a orderline and manages the state of the orderline as documented in states xml

Scenario: Create a new orderline
Given that "flowName" equals "order-flow"
And that "initialState" equals "OPENED"
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I POST a REST request to URL "/orderline" with payload
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

Scenario: Retrieve the orderline that just got created
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I GET a REST request to URL "/orderline/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"


Scenario: Send the orderConfirm event to the orderline with comments
Given that "comment" equals "Comment for orderConfirm"
And that "event" equals "orderConfirm"
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I PATCH a REST request to URL "/orderline/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PENDING_BILLING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
