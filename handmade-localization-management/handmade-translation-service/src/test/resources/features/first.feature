Feature: Tests the translation Workflow Service using a REST client. This is done only for the
first testcase. Translation service exists and is under test.
It helps to create a translation and manages the state of the translation as documented in states xml
Scenario: Create a new translation
Given that "flowName" equals "translationFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/translation" with payload
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

Scenario: Retrieve the translation that just got created
When I GET a REST request to URL "/translation/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the submitForReview event to the translation with comments
 Given that "comment" equals "Comment for submitForReview"
 And that "event" equals "submitForReview"
When I PATCH a REST request to URL "/translation/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the approve event to the translation with comments
 Given that "comment" equals "Comment for approve"
 And that "event" equals "approve"
When I PATCH a REST request to URL "/translation/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the publish event to the translation with comments
 Given that "comment" equals "Comment for publish"
 And that "event" equals "publish"
When I PATCH a REST request to URL "/translation/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PUBLISHED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to translation . This will err out.
When I PATCH a REST request to URL "/translation/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

