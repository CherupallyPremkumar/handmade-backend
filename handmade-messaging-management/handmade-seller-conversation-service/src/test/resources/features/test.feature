Feature: Testcase ID 
Tests the sellerconversation Workflow Service using a REST client. Sellerconversation service exists and is under test.
It helps to create a sellerconversation and manages the state of the sellerconversation as documented in states xml
Scenario: Create a new sellerconversation
Given that "flowName" equals "sellerConversationFlow"
And that "initialState" equals "OPEN"
When I POST a REST request to URL "/sellerconversation" with payload
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

Scenario: Retrieve the sellerconversation that just got created
When I GET a REST request to URL "/sellerconversation/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the assign event to the sellerconversation with comments
Given that "comment" equals "Comment for assign"
And that "event" equals "assign"
When I PATCH a REST request to URL "/sellerconversation/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ASSIGNED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the respond event to the sellerconversation with comments
Given that "comment" equals "Comment for respond"
And that "event" equals "respond"
When I PATCH a REST request to URL "/sellerconversation/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "AWAITING_CUSTOMER"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the sellerconversation Workflow Service using a REST client. Sellerconversation service exists and is under test.
It helps to create a sellerconversation and manages the state of the sellerconversation as documented in states xml
Scenario: Create a new sellerconversation
Given that "flowName" equals "sellerConversationFlow"
And that "initialState" equals "OPEN"
When I POST a REST request to URL "/sellerconversation" with payload
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

Scenario: Retrieve the sellerconversation that just got created
When I GET a REST request to URL "/sellerconversation/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the assign event to the sellerconversation with comments
Given that "comment" equals "Comment for assign"
And that "event" equals "assign"
When I PATCH a REST request to URL "/sellerconversation/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ASSIGNED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the close event to the sellerconversation with comments
Given that "comment" equals "Comment for close"
And that "event" equals "close"
When I PATCH a REST request to URL "/sellerconversation/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CLOSED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the sellerconversation Workflow Service using a REST client. Sellerconversation service exists and is under test.
It helps to create a sellerconversation and manages the state of the sellerconversation as documented in states xml
Scenario: Create a new sellerconversation
Given that "flowName" equals "sellerConversationFlow"
And that "initialState" equals "OPEN"
When I POST a REST request to URL "/sellerconversation" with payload
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

Scenario: Retrieve the sellerconversation that just got created
When I GET a REST request to URL "/sellerconversation/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the assign event to the sellerconversation with comments
Given that "comment" equals "Comment for assign"
And that "event" equals "assign"
When I PATCH a REST request to URL "/sellerconversation/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ASSIGNED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the escalate event to the sellerconversation with comments
Given that "comment" equals "Comment for escalate"
And that "event" equals "escalate"
When I PATCH a REST request to URL "/sellerconversation/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ESCALATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
