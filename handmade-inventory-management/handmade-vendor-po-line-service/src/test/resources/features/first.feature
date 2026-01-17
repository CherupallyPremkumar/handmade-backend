Feature: Tests the vendorpoline Workflow Service using a REST client. This is done only for the
first testcase. Vendorpoline service exists and is under test.
It helps to create a vendorpoline and manages the state of the vendorpoline as documented in states xml
Scenario: Create a new vendorpoline
Given that "flowName" equals "vendorPOLineFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/vendorpoline" with payload
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

Scenario: Retrieve the vendorpoline that just got created
When I GET a REST request to URL "/vendorpoline/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the cancel event to the vendorpoline with comments
 Given that "comment" equals "Comment for cancel"
 And that "event" equals "cancel"
When I PATCH a REST request to URL "/vendorpoline/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to vendorpoline . This will err out.
When I PATCH a REST request to URL "/vendorpoline/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

