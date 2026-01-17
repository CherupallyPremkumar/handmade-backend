Feature: Testcase ID 
Tests the notificationlog Workflow Service using a REST client. Notificationlog service exists and is under test.
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

Scenario: Send the bounce event to the notificationlog with comments
Given that "comment" equals "Comment for bounce"
And that "event" equals "bounce"
When I PATCH a REST request to URL "/notificationlog/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "BOUNCED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the notificationlog Workflow Service using a REST client. Notificationlog service exists and is under test.
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

Scenario: Send the deliver event to the notificationlog with comments
Given that "comment" equals "Comment for deliver"
And that "event" equals "deliver"
When I PATCH a REST request to URL "/notificationlog/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DELIVERED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the click event to the notificationlog with comments
Given that "comment" equals "Comment for click"
And that "event" equals "click"
When I PATCH a REST request to URL "/notificationlog/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CLICKED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the notificationlog Workflow Service using a REST client. Notificationlog service exists and is under test.
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

Scenario: Send the deliver event to the notificationlog with comments
Given that "comment" equals "Comment for deliver"
And that "event" equals "deliver"
When I PATCH a REST request to URL "/notificationlog/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DELIVERED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the open event to the notificationlog with comments
Given that "comment" equals "Comment for open"
And that "event" equals "open"
When I PATCH a REST request to URL "/notificationlog/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "OPENED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the click event to the notificationlog with comments
Given that "comment" equals "Comment for click"
And that "event" equals "click"
When I PATCH a REST request to URL "/notificationlog/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CLICKED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
