Feature: Testcase ID 
Tests the shipment Workflow Service using a REST client. Shipment service exists and is under test.
It helps to create a shipment and manages the state of the shipment as documented in states xml
Scenario: Create a new shipment
Given that "flowName" equals "shipmentFlow"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/shipment" with payload
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

Scenario: Retrieve the shipment that just got created
When I GET a REST request to URL "/shipment/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the book event to the shipment with comments
Given that "comment" equals "Comment for book"
And that "event" equals "book"
When I PATCH a REST request to URL "/shipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "BOOKED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the pickup event to the shipment with comments
Given that "comment" equals "Comment for pickup"
And that "event" equals "pickup"
When I PATCH a REST request to URL "/shipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PICKED_UP"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the transit event to the shipment with comments
Given that "comment" equals "Comment for transit"
And that "event" equals "transit"
When I PATCH a REST request to URL "/shipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_TRANSIT"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the exception event to the shipment with comments
Given that "comment" equals "Comment for exception"
And that "event" equals "exception"
When I PATCH a REST request to URL "/shipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "EXCEPTION"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the returnToSender event to the shipment with comments
Given that "comment" equals "Comment for returnToSender"
And that "event" equals "returnToSender"
When I PATCH a REST request to URL "/shipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RETURNED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the shipment Workflow Service using a REST client. Shipment service exists and is under test.
It helps to create a shipment and manages the state of the shipment as documented in states xml
Scenario: Create a new shipment
Given that "flowName" equals "shipmentFlow"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/shipment" with payload
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

Scenario: Retrieve the shipment that just got created
When I GET a REST request to URL "/shipment/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the book event to the shipment with comments
Given that "comment" equals "Comment for book"
And that "event" equals "book"
When I PATCH a REST request to URL "/shipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "BOOKED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the pickup event to the shipment with comments
Given that "comment" equals "Comment for pickup"
And that "event" equals "pickup"
When I PATCH a REST request to URL "/shipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PICKED_UP"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the transit event to the shipment with comments
Given that "comment" equals "Comment for transit"
And that "event" equals "transit"
When I PATCH a REST request to URL "/shipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_TRANSIT"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the outForDelivery event to the shipment with comments
Given that "comment" equals "Comment for outForDelivery"
And that "event" equals "outForDelivery"
When I PATCH a REST request to URL "/shipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "OUT_FOR_DELIVERY"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the exception event to the shipment with comments
Given that "comment" equals "Comment for exception"
And that "event" equals "exception"
When I PATCH a REST request to URL "/shipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "EXCEPTION"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the returnToSender event to the shipment with comments
Given that "comment" equals "Comment for returnToSender"
And that "event" equals "returnToSender"
When I PATCH a REST request to URL "/shipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RETURNED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the shipment Workflow Service using a REST client. Shipment service exists and is under test.
It helps to create a shipment and manages the state of the shipment as documented in states xml
Scenario: Create a new shipment
Given that "flowName" equals "shipmentFlow"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/shipment" with payload
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

Scenario: Retrieve the shipment that just got created
When I GET a REST request to URL "/shipment/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the book event to the shipment with comments
Given that "comment" equals "Comment for book"
And that "event" equals "book"
When I PATCH a REST request to URL "/shipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "BOOKED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the pickup event to the shipment with comments
Given that "comment" equals "Comment for pickup"
And that "event" equals "pickup"
When I PATCH a REST request to URL "/shipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PICKED_UP"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the transit event to the shipment with comments
Given that "comment" equals "Comment for transit"
And that "event" equals "transit"
When I PATCH a REST request to URL "/shipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_TRANSIT"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the outForDelivery event to the shipment with comments
Given that "comment" equals "Comment for outForDelivery"
And that "event" equals "outForDelivery"
When I PATCH a REST request to URL "/shipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "OUT_FOR_DELIVERY"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the deliver event to the shipment with comments
Given that "comment" equals "Comment for deliver"
And that "event" equals "deliver"
When I PATCH a REST request to URL "/shipment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DELIVERED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
