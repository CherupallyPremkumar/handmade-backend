Feature: Testcase ID 
Tests the inboundshipmentitem Workflow Service using a REST client. Inboundshipmentitem service exists and is under test.
It helps to create a inboundshipmentitem and manages the state of the inboundshipmentitem as documented in states xml
Scenario: Create a new inboundshipmentitem
Given that "flowName" equals "inboundShipmentItemFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/inboundshipmentitem" with payload
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

Scenario: Retrieve the inboundshipmentitem that just got created
When I GET a REST request to URL "/inboundshipmentitem/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the damage event to the inboundshipmentitem with comments
Given that "comment" equals "Comment for damage"
And that "event" equals "damage"
When I PATCH a REST request to URL "/inboundshipmentitem/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DAMAGED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
