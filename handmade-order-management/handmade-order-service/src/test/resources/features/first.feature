Feature: Tests the order Workflow Service using a REST client. This is done only for the
first testcase. Order service exists and is under test.
It helps to create a order and manages the state of the order as documented in states xml
Scenario: Create a new order
Given that "flowName" equals "orderFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/order" with payload
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

Scenario: Retrieve the order that just got created
When I GET a REST request to URL "/order/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the confirm event to the order with comments
 Given that "comment" equals "Comment for confirm"
 And that "event" equals "confirm"
When I PATCH a REST request to URL "/order/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CONFIRMED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the cancel event to the order with comments
 Given that "comment" equals "Comment for cancel"
 And that "event" equals "cancel"
When I PATCH a REST request to URL "/order/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to order . This will err out.
When I PATCH a REST request to URL "/order/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

