Feature: Testcase ID 
Tests the notification Workflow Service using a REST client. Notification service exists and is under test.
It helps to create a notification and manages the state of the notification as documented in states xml
Scenario: Create a new notification
When I POST a REST request to URL "/notification" with payload
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

Scenario: Retrieve the notification that just got created
When I GET a REST request to URL "/notification/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Feature: Testcase ID 
Tests the notification Workflow Service using a REST client. Notification service exists and is under test.
It helps to create a notification and manages the state of the notification as documented in states xml
Scenario: Create a new notification
When I POST a REST request to URL "/notification" with payload
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

Scenario: Retrieve the notification that just got created
When I GET a REST request to URL "/notification/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

