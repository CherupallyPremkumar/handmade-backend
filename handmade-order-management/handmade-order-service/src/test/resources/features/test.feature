Feature: Testcase ID 
Tests the order Workflow Service using a REST client. Order service exists and is under test.
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
Scenario: Send the pay event to the order with comments
Given that "comment" equals "Comment for pay"
And that "event" equals "pay"
When I PATCH a REST request to URL "/order/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PAID"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the process event to the order with comments
Given that "comment" equals "Comment for process"
And that "event" equals "process"
When I PATCH a REST request to URL "/order/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PROCESSING"
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
Feature: Testcase ID 
Tests the order Workflow Service using a REST client. Order service exists and is under test.
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
Scenario: Send the pay event to the order with comments
Given that "comment" equals "Comment for pay"
And that "event" equals "pay"
When I PATCH a REST request to URL "/order/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PAID"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the process event to the order with comments
Given that "comment" equals "Comment for process"
And that "event" equals "process"
When I PATCH a REST request to URL "/order/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PROCESSING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the ship event to the order with comments
Given that "comment" equals "Comment for ship"
And that "event" equals "ship"
When I PATCH a REST request to URL "/order/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SHIPPED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the deliver event to the order with comments
Given that "comment" equals "Comment for deliver"
And that "event" equals "deliver"
When I PATCH a REST request to URL "/order/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DELIVERED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the complete event to the order with comments
Given that "comment" equals "Comment for complete"
And that "event" equals "complete"
When I PATCH a REST request to URL "/order/${id}/${event}" with payload
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
Tests the order Workflow Service using a REST client. Order service exists and is under test.
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
Scenario: Send the pay event to the order with comments
Given that "comment" equals "Comment for pay"
And that "event" equals "pay"
When I PATCH a REST request to URL "/order/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PAID"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the process event to the order with comments
Given that "comment" equals "Comment for process"
And that "event" equals "process"
When I PATCH a REST request to URL "/order/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PROCESSING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the ship event to the order with comments
Given that "comment" equals "Comment for ship"
And that "event" equals "ship"
When I PATCH a REST request to URL "/order/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SHIPPED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the deliver event to the order with comments
Given that "comment" equals "Comment for deliver"
And that "event" equals "deliver"
When I PATCH a REST request to URL "/order/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DELIVERED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the return event to the order with comments
Given that "comment" equals "Comment for return"
And that "event" equals "return"
When I PATCH a REST request to URL "/order/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RETURN_REQUESTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the approveReturn event to the order with comments
Given that "comment" equals "Comment for approveReturn"
And that "event" equals "approveReturn"
When I PATCH a REST request to URL "/order/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RETURNED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the refund event to the order with comments
Given that "comment" equals "Comment for refund"
And that "event" equals "refund"
When I PATCH a REST request to URL "/order/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REFUNDED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the order Workflow Service using a REST client. Order service exists and is under test.
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
