Feature: Tests the productreview Workflow Service using a REST client. This is done only for the
first testcase. Productreview service exists and is under test.
It helps to create a productreview and manages the state of the productreview as documented in states xml

Scenario: Create a new productreview
Given that "flowName" equals "productReviewFlow"
And that "initialState" equals "SUBMITTED"
When I POST a REST request to URL "/productreview" with payload
"""json
{
    "productId": "prod-first-001",
    "customerId": "cust-first-001",
    "rating": 4,
    "title": "Nice product",
    "reviewText": "It works as expected."
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.productId" is "prod-first-001"

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

Scenario: Send the reject event to the productreview with comments
Given that "comment" equals "Comment for reject"
And that "event" equals "reject"
When I PATCH a REST request to URL "/productreview/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REJECTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send an invalid event to productreview. This will err out.
When I PATCH a REST request to URL "/productreview/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422
