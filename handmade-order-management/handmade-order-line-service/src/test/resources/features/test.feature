Feature: Testcase ID 
Tests the orderline Workflow Service using a REST client. Orderline service exists and is under test.
It helps to create a orderline and manages the state of the orderline as documented in states xml
Scenario: Create a new orderline
Given that "flowName" equals "orderLineFlow"
And that "initialState" equals "PENDING"
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
When I GET a REST request to URL "/orderline/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the allocate event to the orderline with comments
Given that "comment" equals "Comment for allocate"
And that "event" equals "allocate"
When I PATCH a REST request to URL "/orderline/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ALLOCATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
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
Feature: Testcase ID 
Tests the orderline Workflow Service using a REST client. Orderline service exists and is under test.
It helps to create a orderline and manages the state of the orderline as documented in states xml
Scenario: Create a new orderline
Given that "flowName" equals "orderLineFlow"
And that "initialState" equals "PENDING"
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
When I GET a REST request to URL "/orderline/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the allocate event to the orderline with comments
Given that "comment" equals "Comment for allocate"
And that "event" equals "allocate"
When I PATCH a REST request to URL "/orderline/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ALLOCATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the pick event to the orderline with comments
Given that "comment" equals "Comment for pick"
And that "event" equals "pick"
When I PATCH a REST request to URL "/orderline/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PICKED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the pack event to the orderline with comments
Given that "comment" equals "Comment for pack"
And that "event" equals "pack"
When I PATCH a REST request to URL "/orderline/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PACKED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the ship event to the orderline with comments
Given that "comment" equals "Comment for ship"
And that "event" equals "ship"
When I PATCH a REST request to URL "/orderline/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SHIPPED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the deliver event to the orderline with comments
Given that "comment" equals "Comment for deliver"
And that "event" equals "deliver"
When I PATCH a REST request to URL "/orderline/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DELIVERED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the complete event to the orderline with comments
Given that "comment" equals "Comment for complete"
And that "event" equals "complete"
When I PATCH a REST request to URL "/orderline/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "COMPLETED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the orderline Workflow Service using a REST client. Orderline service exists and is under test.
It helps to create a orderline and manages the state of the orderline as documented in states xml
Scenario: Create a new orderline
Given that "flowName" equals "orderLineFlow"
And that "initialState" equals "PENDING"
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
When I GET a REST request to URL "/orderline/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the allocate event to the orderline with comments
Given that "comment" equals "Comment for allocate"
And that "event" equals "allocate"
When I PATCH a REST request to URL "/orderline/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ALLOCATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the pick event to the orderline with comments
Given that "comment" equals "Comment for pick"
And that "event" equals "pick"
When I PATCH a REST request to URL "/orderline/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PICKED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the pack event to the orderline with comments
Given that "comment" equals "Comment for pack"
And that "event" equals "pack"
When I PATCH a REST request to URL "/orderline/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PACKED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the ship event to the orderline with comments
Given that "comment" equals "Comment for ship"
And that "event" equals "ship"
When I PATCH a REST request to URL "/orderline/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SHIPPED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the deliver event to the orderline with comments
Given that "comment" equals "Comment for deliver"
And that "event" equals "deliver"
When I PATCH a REST request to URL "/orderline/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DELIVERED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the return event to the orderline with comments
Given that "comment" equals "Comment for return"
And that "event" equals "return"
When I PATCH a REST request to URL "/orderline/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RETURNED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
