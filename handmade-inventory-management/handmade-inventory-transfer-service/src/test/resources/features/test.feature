Feature: Testcase ID 
Tests the inventorytransfer Workflow Service using a REST client. Inventorytransfer service exists and is under test.
It helps to create a inventorytransfer and manages the state of the inventorytransfer as documented in states xml
Scenario: Create a new inventorytransfer
Given that "flowName" equals "inventoryTransferFlow"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/inventorytransfer" with payload
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

Scenario: Retrieve the inventorytransfer that just got created
When I GET a REST request to URL "/inventorytransfer/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the pick event to the inventorytransfer with comments
Given that "comment" equals "Comment for pick"
And that "event" equals "pick"
When I PATCH a REST request to URL "/inventorytransfer/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PICKING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the dispatch event to the inventorytransfer with comments
Given that "comment" equals "Comment for dispatch"
And that "event" equals "dispatch"
When I PATCH a REST request to URL "/inventorytransfer/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_TRANSIT"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the receive event to the inventorytransfer with comments
Given that "comment" equals "Comment for receive"
And that "event" equals "receive"
When I PATCH a REST request to URL "/inventorytransfer/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RECEIVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the inventorytransfer Workflow Service using a REST client. Inventorytransfer service exists and is under test.
It helps to create a inventorytransfer and manages the state of the inventorytransfer as documented in states xml
Scenario: Create a new inventorytransfer
Given that "flowName" equals "inventoryTransferFlow"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/inventorytransfer" with payload
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

Scenario: Retrieve the inventorytransfer that just got created
When I GET a REST request to URL "/inventorytransfer/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the pick event to the inventorytransfer with comments
Given that "comment" equals "Comment for pick"
And that "event" equals "pick"
When I PATCH a REST request to URL "/inventorytransfer/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PICKING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the dispatch event to the inventorytransfer with comments
Given that "comment" equals "Comment for dispatch"
And that "event" equals "dispatch"
When I PATCH a REST request to URL "/inventorytransfer/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_TRANSIT"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the partialReceive event to the inventorytransfer with comments
Given that "comment" equals "Comment for partialReceive"
And that "event" equals "partialReceive"
When I PATCH a REST request to URL "/inventorytransfer/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PARTIALLY_RECEIVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the receiveRemaining event to the inventorytransfer with comments
Given that "comment" equals "Comment for receiveRemaining"
And that "event" equals "receiveRemaining"
When I PATCH a REST request to URL "/inventorytransfer/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RECEIVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
