Feature: Tests the notificationlog Workflow Service using a REST client. This is done only for the
first testcase. Notificationlog service exists and is under test.
It helps to create a notificationlog and manages the state of the notificationlog as documented in states xml
Scenario: Create a new notificationlog
Given that "flowName" equals "notificationLogFlow"
And that "initialState" equals "SENT"
When I POST a REST request to URL "/notificationlog" with payload
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

Scenario: Retrieve the notificationlog that just got created
When I GET a REST request to URL "/notificationlog/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the fail event to the notificationlog with comments
 Given that "comment" equals "Comment for fail"
 And that "event" equals "fail"
When I PATCH a REST request to URL "/notificationlog/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FAILED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to notificationlog . This will err out.
When I PATCH a REST request to URL "/notificationlog/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

