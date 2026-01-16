Feature: Tests the logistics Workflow Service using a REST client. This is done only for the
first testcase. Logistics service exists and is under test.
It helps to create a logistics and manages the state of the logistics as documented in states xml
Scenario: Create a new logistics
Given that "flowName" equals "DELIVERY_ATTEMPT_FLOW"
And that "initialState" equals "SCHEDULED"
When I POST a REST request to URL "/logistics" with payload
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

Scenario: Retrieve the logistics that just got created
When I GET a REST request to URL "/logistics/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the startAttempt event to the logistics with comments
 Given that "comment" equals "Comment for startAttempt"
 And that "event" equals "startAttempt"
When I PATCH a REST request to URL "/logistics/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_PROGRESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the markDelivered event to the logistics with comments
 Given that "comment" equals "Comment for markDelivered"
 And that "event" equals "markDelivered"
When I PATCH a REST request to URL "/logistics/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DELIVERED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to logistics . This will err out.
When I PATCH a REST request to URL "/logistics/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

