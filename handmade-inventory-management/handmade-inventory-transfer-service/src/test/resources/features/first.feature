Feature: Tests the inventorytransfer Workflow Service using a REST client. This is done only for the
first testcase. Inventorytransfer service exists and is under test.
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

 Scenario: Send the cancel event to the inventorytransfer with comments
 Given that "comment" equals "Comment for cancel"
 And that "event" equals "cancel"
When I PATCH a REST request to URL "/inventorytransfer/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to inventorytransfer . This will err out.
When I PATCH a REST request to URL "/inventorytransfer/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

