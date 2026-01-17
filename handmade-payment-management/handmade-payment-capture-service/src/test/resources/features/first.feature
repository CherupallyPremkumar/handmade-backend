Feature: Tests the paymentcapture Workflow Service using a REST client. This is done only for the
first testcase. Paymentcapture service exists and is under test.
It helps to create a paymentcapture and manages the state of the paymentcapture as documented in states xml
Scenario: Create a new paymentcapture
Given that "flowName" equals "paymentCaptureFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/paymentcapture" with payload
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

Scenario: Retrieve the paymentcapture that just got created
When I GET a REST request to URL "/paymentcapture/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the cancel event to the paymentcapture with comments
 Given that "comment" equals "Comment for cancel"
 And that "event" equals "cancel"
When I PATCH a REST request to URL "/paymentcapture/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to paymentcapture . This will err out.
When I PATCH a REST request to URL "/paymentcapture/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

