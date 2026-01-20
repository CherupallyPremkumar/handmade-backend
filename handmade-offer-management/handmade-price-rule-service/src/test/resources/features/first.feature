Feature: Tests the pricerule Workflow Service using a REST client. This is done only for the
first testcase. Pricerule service exists and is under test.
It helps to create a pricerule and manages the state of the pricerule as documented in states xml
Scenario: Create a new pricerule
Given that "flowName" equals "priceRuleFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/pricerule" with payload
"""json
{
    "offerId": "OFFER-001",
    "ruleType": "DISCOUNT",
    "ruleExpression": "amount > 100"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.offerId" is "OFFER-001"

Scenario: Retrieve the pricerule that just got created
When I GET a REST request to URL "/pricerule/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the submit event to the pricerule with comments
 Given that "comment" equals "Comment for submit"
 And that "event" equals "submit"
When I PATCH a REST request to URL "/pricerule/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the approve event to the pricerule with comments
 Given that "comment" equals "Comment for approve"
 And that "event" equals "approve"
When I PATCH a REST request to URL "/pricerule/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the activate event to the pricerule with comments
 Given that "comment" equals "Comment for activate"
 And that "event" equals "activate"
When I PATCH a REST request to URL "/pricerule/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the deprecate event to the pricerule with comments
 Given that "comment" equals "Comment for deprecate"
 And that "event" equals "deprecate"
When I PATCH a REST request to URL "/pricerule/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DEPRECATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to pricerule . This will err out.
When I PATCH a REST request to URL "/pricerule/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

