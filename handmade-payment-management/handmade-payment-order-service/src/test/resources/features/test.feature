Feature: Testcase ID 
Tests the paymentorder Workflow Service using a REST client. Paymentorder service exists and is under test.
It helps to create a paymentorder and manages the state of the paymentorder as documented in states xml
Scenario: Create a new paymentorder
Given that "flowName" equals "paymentOrderFlow"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/paymentorder" with payload
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

Scenario: Retrieve the paymentorder that just got created
When I GET a REST request to URL "/paymentorder/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the initiate event to the paymentorder with comments
Given that "comment" equals "Comment for initiate"
And that "event" equals "initiate"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "INITIATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the fail event to the paymentorder with comments
Given that "comment" equals "Comment for fail"
And that "event" equals "fail"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FAILED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the paymentorder Workflow Service using a REST client. Paymentorder service exists and is under test.
It helps to create a paymentorder and manages the state of the paymentorder as documented in states xml
Scenario: Create a new paymentorder
Given that "flowName" equals "paymentOrderFlow"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/paymentorder" with payload
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

Scenario: Retrieve the paymentorder that just got created
When I GET a REST request to URL "/paymentorder/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the initiate event to the paymentorder with comments
Given that "comment" equals "Comment for initiate"
And that "event" equals "initiate"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "INITIATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the authorize event to the paymentorder with comments
Given that "comment" equals "Comment for authorize"
And that "event" equals "authorize"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "AUTHORIZED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the void event to the paymentorder with comments
Given that "comment" equals "Comment for void"
And that "event" equals "void"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "VOIDED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the paymentorder Workflow Service using a REST client. Paymentorder service exists and is under test.
It helps to create a paymentorder and manages the state of the paymentorder as documented in states xml
Scenario: Create a new paymentorder
Given that "flowName" equals "paymentOrderFlow"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/paymentorder" with payload
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

Scenario: Retrieve the paymentorder that just got created
When I GET a REST request to URL "/paymentorder/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the initiate event to the paymentorder with comments
Given that "comment" equals "Comment for initiate"
And that "event" equals "initiate"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "INITIATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the authorize event to the paymentorder with comments
Given that "comment" equals "Comment for authorize"
And that "event" equals "authorize"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "AUTHORIZED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the capture event to the paymentorder with comments
Given that "comment" equals "Comment for capture"
And that "event" equals "capture"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CAPTURED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the settle event to the paymentorder with comments
Given that "comment" equals "Comment for settle"
And that "event" equals "settle"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SETTLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the paymentorder Workflow Service using a REST client. Paymentorder service exists and is under test.
It helps to create a paymentorder and manages the state of the paymentorder as documented in states xml
Scenario: Create a new paymentorder
Given that "flowName" equals "paymentOrderFlow"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/paymentorder" with payload
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

Scenario: Retrieve the paymentorder that just got created
When I GET a REST request to URL "/paymentorder/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the initiate event to the paymentorder with comments
Given that "comment" equals "Comment for initiate"
And that "event" equals "initiate"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "INITIATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the authorize event to the paymentorder with comments
Given that "comment" equals "Comment for authorize"
And that "event" equals "authorize"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "AUTHORIZED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the capture event to the paymentorder with comments
Given that "comment" equals "Comment for capture"
And that "event" equals "capture"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CAPTURED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the refund event to the paymentorder with comments
Given that "comment" equals "Comment for refund"
And that "event" equals "refund"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
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
Tests the paymentorder Workflow Service using a REST client. Paymentorder service exists and is under test.
It helps to create a paymentorder and manages the state of the paymentorder as documented in states xml
Scenario: Create a new paymentorder
Given that "flowName" equals "paymentOrderFlow"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/paymentorder" with payload
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

Scenario: Retrieve the paymentorder that just got created
When I GET a REST request to URL "/paymentorder/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the cancel event to the paymentorder with comments
Given that "comment" equals "Comment for cancel"
And that "event" equals "cancel"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
