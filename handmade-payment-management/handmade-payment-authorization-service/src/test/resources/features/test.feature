Feature: Testcase ID 
Tests the paymentauthorization Workflow Service using a REST client. Paymentauthorization service exists and is under test.
It helps to create a paymentauthorization and manages the state of the paymentauthorization as documented in states xml
Scenario: Create a new paymentauthorization
Given that "flowName" equals "paymentAuthorizationFlow"
And that "initialState" equals "AUTHORIZED"
When I POST a REST request to URL "/paymentauthorization" with payload
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

Scenario: Retrieve the paymentauthorization that just got created
When I GET a REST request to URL "/paymentauthorization/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the expire event to the paymentauthorization with comments
Given that "comment" equals "Comment for expire"
And that "event" equals "expire"
When I PATCH a REST request to URL "/paymentauthorization/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "EXPIRED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the paymentauthorization Workflow Service using a REST client. Paymentauthorization service exists and is under test.
It helps to create a paymentauthorization and manages the state of the paymentauthorization as documented in states xml
Scenario: Create a new paymentauthorization
Given that "flowName" equals "paymentAuthorizationFlow"
And that "initialState" equals "AUTHORIZED"
When I POST a REST request to URL "/paymentauthorization" with payload
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

Scenario: Retrieve the paymentauthorization that just got created
When I GET a REST request to URL "/paymentauthorization/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the capture event to the paymentauthorization with comments
Given that "comment" equals "Comment for capture"
And that "event" equals "capture"
When I PATCH a REST request to URL "/paymentauthorization/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CAPTURED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
