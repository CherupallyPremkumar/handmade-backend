Feature: Tests the inboundshipment Workflow Service using a REST client. This is done only for the
first testcase. Inboundshipment service exists and is under test.
It helps to create a inboundshipment and manages the state of the inboundshipment as documented in states xml
Scenario: Create a new inboundshipment
Given that "flowName" equals "inboundShipmentFlow"
And that "initialState" equals "EXPECTED"
When I POST a REST request to URL "/inboundshipment" with payload
"""json
{
    "fulfillmentNodeId": "node-123",
    "sellerId": "seller-456",
    "shipmentReference": "REF-789"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.fulfillmentNodeId" is "node-123"

Scenario: Retrieve the inboundshipment that just got created
When I GET a REST request to URL "/inboundshipment/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the receive event to the inboundshipment with comments
 Given that "comment" equals "Comment for receive"
 And that "event" equals "receive"
When I PATCH a REST request to URL "/inboundshipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RECEIVING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the cancel event to the inboundshipment with comments
 Given that "comment" equals "Comment for cancel"
 And that "event" equals "cancel"
When I PATCH a REST request to URL "/inboundshipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to inboundshipment . This will err out.
When I PATCH a REST request to URL "/inboundshipment/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

