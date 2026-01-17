Feature: Tests the financialtransaction Workflow Service using a REST client. This is done only for the
first testcase. Financialtransaction service exists and is under test.
It helps to create a financialtransaction and manages the state of the financialtransaction as documented in states xml
Scenario: Create a new financialtransaction
Given that "flowName" equals "financialTransactionFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/financialtransaction" with payload
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

Scenario: Retrieve the financialtransaction that just got created
When I GET a REST request to URL "/financialtransaction/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the process event to the financialtransaction with comments
 Given that "comment" equals "Comment for process"
 And that "event" equals "process"
When I PATCH a REST request to URL "/financialtransaction/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PROCESSING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the fail event to the financialtransaction with comments
 Given that "comment" equals "Comment for fail"
 And that "event" equals "fail"
When I PATCH a REST request to URL "/financialtransaction/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FAILED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to financialtransaction . This will err out.
When I PATCH a REST request to URL "/financialtransaction/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

