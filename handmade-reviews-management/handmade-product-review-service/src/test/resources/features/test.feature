Feature: Testcase ID 
Tests the productreview Workflow Service using a REST client. Productreview service exists and is under test.
It helps to create a productreview and manages the state of the productreview as documented in states xml

Scenario: Create a new productreview
Given that "flowName" equals "productReviewFlow"
And that "initialState" equals "SUBMITTED"
When I POST a REST request to URL "/productreview" with payload
"""json
{
    "productId": "prod-001",
    "customerId": "cust-001",
    "rating": 5,
    "title": "Great product",
    "reviewText": "I really loved this product!"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.productId" is "prod-001"

Scenario: Retrieve the productreview that just got created
When I GET a REST request to URL "/productreview/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the review event to the productreview with comments
Given that "comment" equals "Comment for review"
And that "event" equals "review"
When I PATCH a REST request to URL "/productreview/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "UNDER_REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send the approve event to the productreview with comments
Given that "comment" equals "Comment for approve"
And that "event" equals "approve"
When I PATCH a REST request to URL "/productreview/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PUBLISHED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send the flag event to the productreview with comments
Given that "comment" equals "Comment for flag"
And that "event" equals "flag"
When I PATCH a REST request to URL "/productreview/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FLAGGED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send the investigate event to the productreview with comments
Given that "comment" equals "Comment for investigate"
And that "event" equals "investigate"
When I PATCH a REST request to URL "/productreview/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "UNDER_INVESTIGATION"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send the remove event to the productreview with comments
Given that "comment" equals "Comment for remove"
And that "event" equals "remove"
When I PATCH a REST request to URL "/productreview/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REMOVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
