Feature: Testcase ID 
Tests the inventoryreservation Workflow Service using a REST client. Inventoryreservation service exists and is under test.
It helps to create a inventoryreservation and manages the state of the inventoryreservation as documented in states xml
Scenario: Create a new inventoryreservation
Given that "flowName" equals "inventoryReservationFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/inventoryreservation" with payload
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

Scenario: Retrieve the inventoryreservation that just got created
When I GET a REST request to URL "/inventoryreservation/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the confirm event to the inventoryreservation with comments
Given that "comment" equals "Comment for confirm"
And that "event" equals "confirm"
When I PATCH a REST request to URL "/inventoryreservation/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CONFIRMED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the expire event to the inventoryreservation with comments
Given that "comment" equals "Comment for expire"
And that "event" equals "expire"
When I PATCH a REST request to URL "/inventoryreservation/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "EXPIRED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the inventoryreservation Workflow Service using a REST client. Inventoryreservation service exists and is under test.
It helps to create a inventoryreservation and manages the state of the inventoryreservation as documented in states xml
Scenario: Create a new inventoryreservation
Given that "flowName" equals "inventoryReservationFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/inventoryreservation" with payload
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

Scenario: Retrieve the inventoryreservation that just got created
When I GET a REST request to URL "/inventoryreservation/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the cancel event to the inventoryreservation with comments
Given that "comment" equals "Comment for cancel"
And that "event" equals "cancel"
When I PATCH a REST request to URL "/inventoryreservation/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
