Feature: Tests the paymentrefundtransaction Workflow Service using a REST client. This is done only for the
first testcase. Paymentrefundtransaction service exists and is under test.
It helps to create a paymentrefundtransaction and manages the state of the paymentrefundtransaction as documented in states xml
Scenario: Create a new paymentrefundtransaction
Given that "flowName" equals "paymentRefundTransactionFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/paymentrefundtransaction" with payload
"""json
{
    "paymentTransactionId": "txn-test-002",
    "refundAmount": 50.00,
    "currencyCode": "USD",
    "reason": "Cancel path test refund"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.reason" is "Cancel path test refund"

Scenario: Retrieve the paymentrefundtransaction that just got created
When I GET a REST request to URL "/paymentrefundtransaction/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the cancel event to the paymentrefundtransaction with comments
 Given that "comment" equals "Comment for cancel"
 And that "event" equals "cancel"
When I PATCH a REST request to URL "/paymentrefundtransaction/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to paymentrefundtransaction . This will err out.
When I PATCH a REST request to URL "/paymentrefundtransaction/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

