Feature: Testcase ID 
Tests the notificationqueue Workflow Service using a REST client. Notificationqueue service exists and is under test.
It helps to create a notificationqueue and manages the state of the notificationqueue as documented in states xml

Scenario: Create a new notificationqueue
Given that "flowName" equals "notificationQueueFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/notificationqueue" with payload
"""json
{
    "recipientId": "user-123",
    "recipientType": "USER",
    "channel": "EMAIL",
    "templateId": "WELCOME_EMAIL",
    "payload": "{\"name\":\"John Doe\"}"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.recipientId" is "user-123"

Scenario: Retrieve the notificationqueue that just got created
When I GET a REST request to URL "/notificationqueue/${id}"
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the send event to the notificationqueue
Given that "event" equals "send"
When I PATCH a REST request to URL "/notificationqueue/${id}/${event}" with payload
"""json
{
    "comment": "Sending notification"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SENDING"

Scenario: Send the fail event to the notificationqueue
Given that "event" equals "fail"
When I PATCH a REST request to URL "/notificationqueue/${id}/${event}" with payload
"""json
{
    "errorMessage": "SMTP Connection failed"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "FAILED"

Scenario: Send the retry event to the notificationqueue
Given that "event" equals "retry"
When I PATCH a REST request to URL "/notificationqueue/${id}/${event}" with payload
"""json
{
    "comment": "Retrying after failure"
}
"""
Then success is true
And the REST response key "mutatedEntity.currentState.stateId" is "PENDING"

Scenario: Send the send event again for succeed test
When I PATCH a REST request to URL "/notificationqueue/${id}/send" with payload
"""json
{}
"""
Then success is true

Scenario: Send the succeed event to the notificationqueue
Given that "event" equals "succeed"
When I PATCH a REST request to URL "/notificationqueue/${id}/${event}" with payload
"""json
{
    "comment": "Notification sent successfully"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "SENT"

Scenario: Create another queue for abandon test
Given that "flowName" equals "notificationQueueFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/notificationqueue" with payload
"""json
{
    "recipientId": "user-456",
    "channel": "SMS",
    "payload": "text message"
}
"""
Then success is true
And store "$.payload.mutatedEntity.id" from response to "id2"

Scenario: Fail and Abandon it
When I PATCH a REST request to URL "/notificationqueue/${id2}/send" with payload
"""json
{}
"""
And I PATCH a REST request to URL "/notificationqueue/${id2}/fail" with payload
"""json
{}
"""
And I PATCH a REST request to URL "/notificationqueue/${id2}/abandon" with payload
"""json
{
    "comment": "Giving up"
}
"""
Then success is true
And the REST response key "mutatedEntity.currentState.stateId" is "ABANDONED"
