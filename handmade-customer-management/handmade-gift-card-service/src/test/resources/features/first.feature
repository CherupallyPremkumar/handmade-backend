Feature: Tests the giftcard Workflow Service using a REST client. This is done only for the
first testcase. Giftcard service exists and is under test.
It helps to create a giftcard and manages the state of the giftcard as documented in states xml
Scenario: Create a new giftcard
Given that "flowName" equals "giftCardFlow"
And that "initialState" equals "ISSUED"
When I POST a REST request to URL "/giftcard" with payload
"""json
{
    "code": "GIFT-123",
    "initialValue": 100.00,
    "currentValue": 100.00,
    "currencyCode": "USD"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.code" is "GIFT-123"

Scenario: Retrieve the giftcard that just got created
When I GET a REST request to URL "/giftcard/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the activate event to the giftcard with comments
 Given that "comment" equals "Comment for activate"
 And that "event" equals "activate"
When I PATCH a REST request to URL "/giftcard/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the expire event to the giftcard with comments
 Given that "comment" equals "Comment for expire"
 And that "event" equals "expire"
When I PATCH a REST request to URL "/giftcard/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "EXPIRED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to giftcard . This will err out.
When I PATCH a REST request to URL "/giftcard/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

