Feature: Testcase ID 
Tests the logistics Workflow Service using a REST client. Logistics service exists and is under test.
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
Scenario: Send the markFailed event to the logistics with comments
Given that "comment" equals "Comment for markFailed"
And that "event" equals "markFailed"
When I PATCH a REST request to URL "/logistics/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FAILED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
