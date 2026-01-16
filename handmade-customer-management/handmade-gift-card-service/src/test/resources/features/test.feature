Feature: Testcase ID 
Tests the giftcard Workflow Service using a REST client. Giftcard service exists and is under test.
It helps to create a giftcard and manages the state of the giftcard as documented in states xml
Scenario: Create a new giftcard
Given that "flowName" equals "giftCardFlow"
And that "initialState" equals "ISSUED"
When I POST a REST request to URL "/giftcard" with payload
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

Scenario: Retrieve the giftcard that just got created
When I GET a REST request to URL "/giftcard/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the activate event to the giftcard with comments
Given that "comment" equals "Comment for activate"
And that "event" equals "activate"
When I PATCH a REST request to URL "/giftcard/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the redeem event to the giftcard with comments
Given that "comment" equals "Comment for redeem"
And that "event" equals "redeem"
When I PATCH a REST request to URL "/giftcard/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REDEEMED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
