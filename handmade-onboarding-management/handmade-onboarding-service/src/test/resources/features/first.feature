Feature: Tests the onboarding Workflow Service using a REST client. This is done only for the
first testcase. Onboarding service exists and is under test.
It helps to create a onboarding and manages the state of the onboarding as documented in states xml
Scenario: Create a new onboarding
Given that "flowName" equals "SELLER_ONBOARDING_FLOW"
And that "initialState" equals "APPLIED"
When I POST a REST request to URL "/onboarding" with payload
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

Scenario: Retrieve the onboarding that just got created
When I GET a REST request to URL "/onboarding/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the reject event to the onboarding with comments
 Given that "comment" equals "Comment for reject"
 And that "event" equals "reject"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REJECTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to onboarding . This will err out.
When I PATCH a REST request to URL "/onboarding/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

