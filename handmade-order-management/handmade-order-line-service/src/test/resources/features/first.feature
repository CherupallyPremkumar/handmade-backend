Feature: Tests the orderline Workflow Service using a REST client. This is done only for the
first testcase. Orderline service exists and is under test.
It helps to create a orderline and manages the state of the orderline as documented in states xml
Scenario: Create a new orderline
Given that "flowName" equals "orderLineFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/orderline" with payload
"""json
{
    "orderId": "ORD-123",
    "productId": "PROD-456",
    "sellerId": "SELL-789",
    "quantity": 2,
    "unitPrice": 49.99,
    "lineTotal": 99.98
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.orderId" is "ORD-123"

Scenario: Retrieve the orderline that just got created
When I GET a REST request to URL "/orderline/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the cancel event to the orderline with comments
 Given that "comment" equals "Comment for cancel"
 And that "event" equals "cancel"
When I PATCH a REST request to URL "/orderline/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to orderline . This will err out.
When I PATCH a REST request to URL "/orderline/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

