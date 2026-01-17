Feature: Tests the productanswer Workflow Service using a REST client. This is done only for the
first testcase. Productanswer service exists and is under test.
It helps to create a productanswer and manages the state of the productanswer as documented in states xml
Scenario: Create a new productanswer
Given that "flowName" equals "productAnswerFlow"
And that "initialState" equals "SUBMITTED"
When I POST a REST request to URL "/productanswer" with payload
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

Scenario: Retrieve the productanswer that just got created
When I GET a REST request to URL "/productanswer/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the review event to the productanswer with comments
 Given that "comment" equals "Comment for review"
 And that "event" equals "review"
When I PATCH a REST request to URL "/productanswer/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "UNDER_REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the approve event to the productanswer with comments
 Given that "comment" equals "Comment for approve"
 And that "event" equals "approve"
When I PATCH a REST request to URL "/productanswer/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PUBLISHED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the markHelpful event to the productanswer with comments
 Given that "comment" equals "Comment for markHelpful"
 And that "event" equals "markHelpful"
When I PATCH a REST request to URL "/productanswer/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "VERIFIED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the unpublish event to the productanswer with comments
 Given that "comment" equals "Comment for unpublish"
 And that "event" equals "unpublish"
When I PATCH a REST request to URL "/productanswer/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "UNPUBLISHED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to productanswer . This will err out.
When I PATCH a REST request to URL "/productanswer/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

