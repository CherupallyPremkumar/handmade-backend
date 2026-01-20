Feature: Tests the paymenttransaction Workflow Service using a REST client. This is done only for the
first testcase. Paymenttransaction service exists and is under test.
It helps to create a paymenttransaction and manages the state of the paymenttransaction as documented in states xml

Scenario: Create a new paymenttransaction
Given that "flowName" equals "paymentTransactionFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/paymenttransaction" with payload
"""json
{
    "customerId": "cust-first-001",
    "amount": 75.00,
    "currencyCode": "USD",
    "transactionType": "PAYMENT"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.customerId" is "cust-first-001"

Scenario: Retrieve the paymenttransaction that just got created
When I GET a REST request to URL "/paymenttransaction/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the cancel event to the paymenttransaction with comments
Given that "comment" equals "Comment for cancel"
And that "event" equals "cancel"
When I PATCH a REST request to URL "/paymenttransaction/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send an invalid event to paymenttransaction. This will err out.
When I PATCH a REST request to URL "/paymenttransaction/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422
