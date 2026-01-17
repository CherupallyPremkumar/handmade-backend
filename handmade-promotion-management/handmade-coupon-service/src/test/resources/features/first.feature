Feature: Tests the coupon Workflow Service using a REST client. This is done only for the
first testcase. Coupon service exists and is under test.
It helps to create a coupon and manages the state of the coupon as documented in states xml
Scenario: Create a new coupon
Given that "flowName" equals "couponFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/coupon" with payload
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

Scenario: Retrieve the coupon that just got created
When I GET a REST request to URL "/coupon/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the submit event to the coupon with comments
 Given that "comment" equals "Comment for submit"
 And that "event" equals "submit"
When I PATCH a REST request to URL "/coupon/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the approve event to the coupon with comments
 Given that "comment" equals "Comment for approve"
 And that "event" equals "approve"
When I PATCH a REST request to URL "/coupon/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the activate event to the coupon with comments
 Given that "comment" equals "Comment for activate"
 And that "event" equals "activate"
When I PATCH a REST request to URL "/coupon/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the expire event to the coupon with comments
 Given that "comment" equals "Comment for expire"
 And that "event" equals "expire"
When I PATCH a REST request to URL "/coupon/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "EXPIRED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to coupon . This will err out.
When I PATCH a REST request to URL "/coupon/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

