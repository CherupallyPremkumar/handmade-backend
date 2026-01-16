Feature: Testcase ID 
Tests the reviews Workflow Service using a REST client. Reviews service exists and is under test.
It helps to create a reviews and manages the state of the reviews as documented in states xml
Scenario: Create a new reviews
Given that "flowName" equals "productReviewFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/reviews" with payload
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

Scenario: Retrieve the reviews that just got created
When I GET a REST request to URL "/reviews/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the reject event to the reviews with comments
Given that "comment" equals "Comment for reject"
And that "event" equals "reject"
When I PATCH a REST request to URL "/reviews/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REJECTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the appeal event to the reviews with comments
Given that "comment" equals "Comment for appeal"
And that "event" equals "appeal"
When I PATCH a REST request to URL "/reviews/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "APPEAL_REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
