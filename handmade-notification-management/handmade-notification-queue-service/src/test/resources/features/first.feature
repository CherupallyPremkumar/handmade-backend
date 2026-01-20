Feature: Tests the notificationqueue Workflow Service using a REST client. This is done only for the
first testcase. Notificationqueue service exists and is under test.
It helps to create a notificationqueue and manages the state of the notificationqueue as documented in states xml

Scenario: Create a new notificationqueue
Given that "flowName" equals "notificationQueueFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/notificationqueue" with payload
"""json
{
    "recipientId": "user-first-001",
    "recipientType": "USER",
    "channel": "PUSH",
    "payload": "push notification"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.recipientId" is "user-first-001"

Scenario: Retrieve the notificationqueue that just got created
When I GET a REST request to URL "/notificationqueue/${id}"
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the cancel event to the notificationqueue with comments
Given that "comment" equals "Comment for cancel"
And that "event" equals "cancel"
When I PATCH a REST request to URL "/notificationqueue/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send an invalid event to notificationqueue. This will err out.
When I PATCH a REST request to URL "/notificationqueue/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422
