Feature: Testcase ID 
Tests the paymentrefundtransaction Workflow Service using a REST client. Paymentrefundtransaction service exists and is under test.
It helps to create a paymentrefundtransaction and manages the state of the paymentrefundtransaction as documented in states xml
Scenario: Create a new paymentrefundtransaction
Given that "flowName" equals "paymentRefundTransactionFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/paymentrefundtransaction" with payload
"""json
{
    "paymentTransactionId": "txn-test-001",
    "refundAmount": 100.00,
    "currencyCode": "USD",
    "reason": "Test refund reason"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.reason" is "Test refund reason"

Scenario: Retrieve the paymentrefundtransaction that just got created
When I GET a REST request to URL "/paymentrefundtransaction/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the process event to the paymentrefundtransaction with comments
Given that "comment" equals "Comment for process"
And that "event" equals "process"
When I PATCH a REST request to URL "/paymentrefundtransaction/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PROCESSING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the fail event to the paymentrefundtransaction with comments
Given that "comment" equals "Comment for fail"
And that "event" equals "fail"
When I PATCH a REST request to URL "/paymentrefundtransaction/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FAILED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

