Feature: Testcase ID 
Tests the platform Workflow Service using a REST client. Platform service exists and is under test.
It helps to create a platform and manages the state of the platform as documented in states xml
Scenario: Create a new platform
Given that "flowName" equals "platformFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/platform" with payload
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

Scenario: Retrieve the platform that just got created
When I GET a REST request to URL "/platform/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the submit event to the platform with comments
Given that "comment" equals "Comment for submit"
And that "event" equals "submit"
When I PATCH a REST request to URL "/platform/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the approve event to the platform with comments
Given that "comment" equals "Comment for approve"
And that "event" equals "approve"
When I PATCH a REST request to URL "/platform/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the activate event to the platform with comments
Given that "comment" equals "Comment for activate"
And that "event" equals "activate"
When I PATCH a REST request to URL "/platform/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the deactivate event to the platform with comments
Given that "comment" equals "Comment for deactivate"
And that "event" equals "deactivate"
When I PATCH a REST request to URL "/platform/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "INACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the archive event to the platform with comments
Given that "comment" equals "Comment for archive"
And that "event" equals "archive"
When I PATCH a REST request to URL "/platform/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ARCHIVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
