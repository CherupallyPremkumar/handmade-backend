Feature: Tests the payment Workflow Service using a REST client. This is done only for the
first testcase. Payment service exists and is under test.
It helps to create a payment and manages the state of the payment as documented in states xml
Scenario: Create a new payment
Given that "flowName" equals "PAYMENT_FLOW"
And that "initialState" equals "INIT"
When I POST a REST request to URL "/payment" with payload
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

Scenario: Retrieve the payment that just got created
When I GET a REST request to URL "/payment/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the failPayment event to the payment with comments
 Given that "comment" equals "Comment for failPayment"
 And that "event" equals "failPayment"
When I PATCH a REST request to URL "/payment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FAILED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to payment . This will err out.
When I PATCH a REST request to URL "/payment/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

