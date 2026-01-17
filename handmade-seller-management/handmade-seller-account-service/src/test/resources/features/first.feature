Feature: Tests the selleraccount Workflow Service using a REST client. This is done only for the
first testcase. Selleraccount service exists and is under test.
It helps to create a selleraccount and manages the state of the selleraccount as documented in states xml
Scenario: Create a new selleraccount
Given that "flowName" equals "sellerAccountFlow"
And that "initialState" equals "REGISTERED"
When I POST a REST request to URL "/selleraccount" with payload
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

Scenario: Retrieve the selleraccount that just got created
When I GET a REST request to URL "/selleraccount/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the verify event to the selleraccount with comments
 Given that "comment" equals "Comment for verify"
 And that "event" equals "verify"
When I PATCH a REST request to URL "/selleraccount/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "VERIFIED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the activate event to the selleraccount with comments
 Given that "comment" equals "Comment for activate"
 And that "event" equals "activate"
When I PATCH a REST request to URL "/selleraccount/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the suspend event to the selleraccount with comments
 Given that "comment" equals "Comment for suspend"
 And that "event" equals "suspend"
When I PATCH a REST request to URL "/selleraccount/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SUSPENDED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to selleraccount . This will err out.
When I PATCH a REST request to URL "/selleraccount/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

