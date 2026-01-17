Feature: Tests the returnitem Workflow Service using a REST client. This is done only for the
first testcase. Returnitem service exists and is under test.
It helps to create a returnitem and manages the state of the returnitem as documented in states xml
Scenario: Create a new returnitem
Given that "flowName" equals "returnItemFlow"
And that "initialState" equals "REQUESTED"
When I POST a REST request to URL "/returnitem" with payload
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

Scenario: Retrieve the returnitem that just got created
When I GET a REST request to URL "/returnitem/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the approve event to the returnitem with comments
 Given that "comment" equals "Comment for approve"
 And that "event" equals "approve"
When I PATCH a REST request to URL "/returnitem/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the receive event to the returnitem with comments
 Given that "comment" equals "Comment for receive"
 And that "event" equals "receive"
When I PATCH a REST request to URL "/returnitem/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_TRANSIT"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the arrive event to the returnitem with comments
 Given that "comment" equals "Comment for arrive"
 And that "event" equals "arrive"
When I PATCH a REST request to URL "/returnitem/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RECEIVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the inspect event to the returnitem with comments
 Given that "comment" equals "Comment for inspect"
 And that "event" equals "inspect"
When I PATCH a REST request to URL "/returnitem/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "INSPECTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the restock event to the returnitem with comments
 Given that "comment" equals "Comment for restock"
 And that "event" equals "restock"
When I PATCH a REST request to URL "/returnitem/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RESTOCKED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to returnitem . This will err out.
When I PATCH a REST request to URL "/returnitem/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

