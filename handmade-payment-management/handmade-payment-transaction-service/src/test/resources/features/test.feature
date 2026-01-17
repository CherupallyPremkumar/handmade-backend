Feature: Testcase ID 
Tests the paymenttransaction Workflow Service using a REST client. Paymenttransaction service exists and is under test.
It helps to create a paymenttransaction and manages the state of the paymenttransaction as documented in states xml
Scenario: Create a new paymenttransaction
Given that "flowName" equals "paymentTransactionFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/paymenttransaction" with payload
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

Scenario: Retrieve the paymenttransaction that just got created
When I GET a REST request to URL "/paymenttransaction/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the process event to the paymenttransaction with comments
Given that "comment" equals "Comment for process"
And that "event" equals "process"
When I PATCH a REST request to URL "/paymenttransaction/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PROCESSING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the fail event to the paymenttransaction with comments
Given that "comment" equals "Comment for fail"
And that "event" equals "fail"
When I PATCH a REST request to URL "/paymenttransaction/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FAILED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the paymenttransaction Workflow Service using a REST client. Paymenttransaction service exists and is under test.
It helps to create a paymenttransaction and manages the state of the paymenttransaction as documented in states xml
Scenario: Create a new paymenttransaction
Given that "flowName" equals "paymentTransactionFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/paymenttransaction" with payload
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

Scenario: Retrieve the paymenttransaction that just got created
When I GET a REST request to URL "/paymenttransaction/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the process event to the paymenttransaction with comments
Given that "comment" equals "Comment for process"
And that "event" equals "process"
When I PATCH a REST request to URL "/paymenttransaction/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PROCESSING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the succeed event to the paymenttransaction with comments
Given that "comment" equals "Comment for succeed"
And that "event" equals "succeed"
When I PATCH a REST request to URL "/paymenttransaction/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SUCCESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
