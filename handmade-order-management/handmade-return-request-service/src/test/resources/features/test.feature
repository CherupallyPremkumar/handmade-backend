Feature: Testcase ID 
Tests the returnrequest Workflow Service using a REST client. Returnrequest service exists and is under test.
It helps to create a returnrequest and manages the state of the returnrequest as documented in states xml
Scenario: Create a new returnrequest
Given that "flowName" equals "returnRequestFlow"
And that "initialState" equals "SUBMITTED"
When I POST a REST request to URL "/returnrequest" with payload
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

Scenario: Retrieve the returnrequest that just got created
When I GET a REST request to URL "/returnrequest/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the review event to the returnrequest with comments
Given that "comment" equals "Comment for review"
And that "event" equals "review"
When I PATCH a REST request to URL "/returnrequest/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "UNDER_REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the approve event to the returnrequest with comments
Given that "comment" equals "Comment for approve"
And that "event" equals "approve"
When I PATCH a REST request to URL "/returnrequest/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the ship event to the returnrequest with comments
Given that "comment" equals "Comment for ship"
And that "event" equals "ship"
When I PATCH a REST request to URL "/returnrequest/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_TRANSIT"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the receive event to the returnrequest with comments
Given that "comment" equals "Comment for receive"
And that "event" equals "receive"
When I PATCH a REST request to URL "/returnrequest/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RECEIVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the inspect event to the returnrequest with comments
Given that "comment" equals "Comment for inspect"
And that "event" equals "inspect"
When I PATCH a REST request to URL "/returnrequest/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "INSPECTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the reject event to the returnrequest with comments
Given that "comment" equals "Comment for reject"
And that "event" equals "reject"
When I PATCH a REST request to URL "/returnrequest/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REJECTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the returnrequest Workflow Service using a REST client. Returnrequest service exists and is under test.
It helps to create a returnrequest and manages the state of the returnrequest as documented in states xml
Scenario: Create a new returnrequest
Given that "flowName" equals "returnRequestFlow"
And that "initialState" equals "SUBMITTED"
When I POST a REST request to URL "/returnrequest" with payload
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

Scenario: Retrieve the returnrequest that just got created
When I GET a REST request to URL "/returnrequest/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the review event to the returnrequest with comments
Given that "comment" equals "Comment for review"
And that "event" equals "review"
When I PATCH a REST request to URL "/returnrequest/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "UNDER_REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the approve event to the returnrequest with comments
Given that "comment" equals "Comment for approve"
And that "event" equals "approve"
When I PATCH a REST request to URL "/returnrequest/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the ship event to the returnrequest with comments
Given that "comment" equals "Comment for ship"
And that "event" equals "ship"
When I PATCH a REST request to URL "/returnrequest/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_TRANSIT"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the receive event to the returnrequest with comments
Given that "comment" equals "Comment for receive"
And that "event" equals "receive"
When I PATCH a REST request to URL "/returnrequest/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RECEIVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the inspect event to the returnrequest with comments
Given that "comment" equals "Comment for inspect"
And that "event" equals "inspect"
When I PATCH a REST request to URL "/returnrequest/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "INSPECTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the accept event to the returnrequest with comments
Given that "comment" equals "Comment for accept"
And that "event" equals "accept"
When I PATCH a REST request to URL "/returnrequest/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACCEPTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the refund event to the returnrequest with comments
Given that "comment" equals "Comment for refund"
And that "event" equals "refund"
When I PATCH a REST request to URL "/returnrequest/${id}/${event}" with payload
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
Tests the returnrequest Workflow Service using a REST client. Returnrequest service exists and is under test.
It helps to create a returnrequest and manages the state of the returnrequest as documented in states xml
Scenario: Create a new returnrequest
Given that "flowName" equals "returnRequestFlow"
And that "initialState" equals "SUBMITTED"
When I POST a REST request to URL "/returnrequest" with payload
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

Scenario: Retrieve the returnrequest that just got created
When I GET a REST request to URL "/returnrequest/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the review event to the returnrequest with comments
Given that "comment" equals "Comment for review"
And that "event" equals "review"
When I PATCH a REST request to URL "/returnrequest/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "UNDER_REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the reject event to the returnrequest with comments
Given that "comment" equals "Comment for reject"
And that "event" equals "reject"
When I PATCH a REST request to URL "/returnrequest/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REJECTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
