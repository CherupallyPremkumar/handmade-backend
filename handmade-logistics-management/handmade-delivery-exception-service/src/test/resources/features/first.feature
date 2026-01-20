Feature: Tests the deliveryexception Workflow Service using a REST client. This is done only for the
first testcase. Deliveryexception service exists and is under test.
It helps to create a deliveryexception and manages the state of the deliveryexception as documented in states xml
Scenario: Create a new deliveryexception
Given that "flowName" equals "deliveryExceptionFlow"
And that "initialState" equals "REPORTED"
When I POST a REST request to URL "/deliveryexception" with payload
"""json
{
    "description": "Description",
    "trackingNumber": "1Z9999999999999999",
    "exceptionTime": "2024-02-01"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.description" is "Description"

Scenario: Retrieve the deliveryexception that just got created
When I GET a REST request to URL "/deliveryexception/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the acknowledge event to the deliveryexception with comments
 Given that "comment" equals "Comment for acknowledge"
 And that "event" equals "acknowledge"
When I PATCH a REST request to URL "/deliveryexception/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACKNOWLEDGED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the investigate event to the deliveryexception with comments
 Given that "comment" equals "Comment for investigate"
 And that "event" equals "investigate"
When I PATCH a REST request to URL "/deliveryexception/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "INVESTIGATING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the resolve event to the deliveryexception with comments
 Given that "comment" equals "Comment for resolve"
 And that "event" equals "resolve"
When I PATCH a REST request to URL "/deliveryexception/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RESOLVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to deliveryexception . This will err out.
When I PATCH a REST request to URL "/deliveryexception/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

