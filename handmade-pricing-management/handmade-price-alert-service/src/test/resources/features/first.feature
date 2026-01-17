Feature: Tests the pricealert Workflow Service using a REST client. This is done only for the
first testcase. Pricealert service exists and is under test.
It helps to create a pricealert and manages the state of the pricealert as documented in states xml
Scenario: Create a new pricealert
Given that "flowName" equals "priceAlertFlow"
And that "initialState" equals "ACTIVE"
When I POST a REST request to URL "/pricealert" with payload
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

Scenario: Retrieve the pricealert that just got created
When I GET a REST request to URL "/pricealert/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the expire event to the pricealert with comments
 Given that "comment" equals "Comment for expire"
 And that "event" equals "expire"
When I PATCH a REST request to URL "/pricealert/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "EXPIRED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to pricealert . This will err out.
When I PATCH a REST request to URL "/pricealert/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

