Feature: Tests the sellerconversation Workflow Service using a REST client. This is done only for the
first testcase. Sellerconversation service exists and is under test.
It helps to create a sellerconversation and manages the state of the sellerconversation as documented in states xml
Scenario: Create a new sellerconversation
Given that "flowName" equals "sellerConversationFlow"
And that "initialState" equals "OPEN"
When I POST a REST request to URL "/sellerconversation" with payload
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

Scenario: Retrieve the sellerconversation that just got created
When I GET a REST request to URL "/sellerconversation/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the close event to the sellerconversation with comments
 Given that "comment" equals "Comment for close"
 And that "event" equals "close"
When I PATCH a REST request to URL "/sellerconversation/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CLOSED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to sellerconversation . This will err out.
When I PATCH a REST request to URL "/sellerconversation/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

