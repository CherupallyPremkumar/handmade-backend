Feature: Testcase ID 
Tests the financeprofile Workflow Service using a REST client. Financeprofile service exists and is under test.
It helps to create a financeprofile and manages the state of the financeprofile as documented in states xml

Scenario: Create a new financeprofile
Given that "flowName" equals "financeProfileFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/financeprofile" with payload
"""json
{
    "sellerId": "seller-test-001",
    "verificationStatus": "PENDING",
    "riskLevel": "LOW"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.sellerId" is "seller-test-001"

Scenario: Retrieve the financeprofile that just got created
When I GET a REST request to URL "/financeprofile/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the verify event to the financeprofile with comments
Given that "comment" equals "Comment for verify"
And that "event" equals "verify"
When I PATCH a REST request to URL "/financeprofile/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "VERIFIED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send the activate event to the financeprofile with comments
Given that "comment" equals "Comment for activate"
And that "event" equals "activate"
When I PATCH a REST request to URL "/financeprofile/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send the suspend event to the financeprofile with comments
Given that "comment" equals "Comment for suspend"
And that "event" equals "suspend"
When I PATCH a REST request to URL "/financeprofile/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SUSPENDED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
