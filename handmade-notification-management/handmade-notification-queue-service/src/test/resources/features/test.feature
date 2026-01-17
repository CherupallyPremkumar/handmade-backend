Feature: Testcase ID 
Tests the notificationqueue Workflow Service using a REST client. Notificationqueue service exists and is under test.
It helps to create a notificationqueue and manages the state of the notificationqueue as documented in states xml
Scenario: Create a new notificationqueue
Given that "flowName" equals "notificationQueueFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/notificationqueue" with payload
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

Scenario: Retrieve the notificationqueue that just got created
When I GET a REST request to URL "/notificationqueue/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the send event to the notificationqueue with comments
Given that "comment" equals "Comment for send"
And that "event" equals "send"
When I PATCH a REST request to URL "/notificationqueue/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SENDING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the fail event to the notificationqueue with comments
Given that "comment" equals "Comment for fail"
And that "event" equals "fail"
When I PATCH a REST request to URL "/notificationqueue/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FAILED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the abandon event to the notificationqueue with comments
Given that "comment" equals "Comment for abandon"
And that "event" equals "abandon"
When I PATCH a REST request to URL "/notificationqueue/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ABANDONED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the notificationqueue Workflow Service using a REST client. Notificationqueue service exists and is under test.
It helps to create a notificationqueue and manages the state of the notificationqueue as documented in states xml
Scenario: Create a new notificationqueue
Given that "flowName" equals "notificationQueueFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/notificationqueue" with payload
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

Scenario: Retrieve the notificationqueue that just got created
When I GET a REST request to URL "/notificationqueue/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the send event to the notificationqueue with comments
Given that "comment" equals "Comment for send"
And that "event" equals "send"
When I PATCH a REST request to URL "/notificationqueue/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SENDING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the succeed event to the notificationqueue with comments
Given that "comment" equals "Comment for succeed"
And that "event" equals "succeed"
When I PATCH a REST request to URL "/notificationqueue/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SENT"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
