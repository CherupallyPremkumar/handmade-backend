Feature: Tests the sellerstore Workflow Service using a REST client. This is done only for the
first testcase. Sellerstore service exists and is under test.
It helps to create a sellerstore and manages the state of the sellerstore as documented in states xml
Scenario: Create a new sellerstore
Given that "flowName" equals "sellerStoreFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/sellerstore" with payload
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

Scenario: Retrieve the sellerstore that just got created
When I GET a REST request to URL "/sellerstore/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the submit event to the sellerstore with comments
 Given that "comment" equals "Comment for submit"
 And that "event" equals "submit"
When I PATCH a REST request to URL "/sellerstore/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the approve event to the sellerstore with comments
 Given that "comment" equals "Comment for approve"
 And that "event" equals "approve"
When I PATCH a REST request to URL "/sellerstore/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the publish event to the sellerstore with comments
 Given that "comment" equals "Comment for publish"
 And that "event" equals "publish"
When I PATCH a REST request to URL "/sellerstore/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PUBLISHED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the suspend event to the sellerstore with comments
 Given that "comment" equals "Comment for suspend"
 And that "event" equals "suspend"
When I PATCH a REST request to URL "/sellerstore/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SUSPENDED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to sellerstore . This will err out.
When I PATCH a REST request to URL "/sellerstore/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

