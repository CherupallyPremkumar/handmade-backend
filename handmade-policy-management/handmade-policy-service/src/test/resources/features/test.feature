Feature: Testcase ID 
Tests the policy Workflow Service using a REST client. Policy service exists and is under test.
It helps to create a policy and manages the state of the policy as documented in states xml
Scenario: Create a new policy
Given that "flowName" equals "POLICY_DEFINITION_FLOW"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/policy" with payload
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

Scenario: Retrieve the policy that just got created
When I GET a REST request to URL "/policy/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the submitReview event to the policy with comments
Given that "comment" equals "Comment for submitReview"
And that "event" equals "submitReview"
When I PATCH a REST request to URL "/policy/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the rejectPolicy event to the policy with comments
Given that "comment" equals "Comment for rejectPolicy"
And that "event" equals "rejectPolicy"
When I PATCH a REST request to URL "/policy/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REJECTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
