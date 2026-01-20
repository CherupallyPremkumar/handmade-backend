Feature: Tests the payout Workflow Service using a REST client. This is done only for the
first testcase. Payout service exists and is under test.
It helps to create a payout and manages the state of the payout as documented in states xml
Scenario: Create a new payout
Given that "flowName" equals "payoutFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/payout" with payload
"""json
{
    "sellerId": "SELL-789",
    "amount": 2500.00,
    "payoutDate": "2024-05-20T10:00:00.000Z"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.sellerId" is "SELL-789"

Scenario: Retrieve the payout that just got created
When I GET a REST request to URL "/payout/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the process event to the payout with comments
 Given that "comment" equals "Comment for process"
 And that "event" equals "process"
When I PATCH a REST request to URL "/payout/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PROCESSING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the fail event to the payout with comments
 Given that "comment" equals "Comment for fail"
 And that "event" equals "fail"
When I PATCH a REST request to URL "/payout/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FAILED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to payout . This will err out.
When I PATCH a REST request to URL "/payout/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

