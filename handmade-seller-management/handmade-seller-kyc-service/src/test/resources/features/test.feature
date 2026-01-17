Feature: Testcase ID 
Tests the sellerkyc Workflow Service using a REST client. Sellerkyc service exists and is under test.
It helps to create a sellerkyc and manages the state of the sellerkyc as documented in states xml
Scenario: Create a new sellerkyc
Given that "flowName" equals "sellerKycFlow"
And that "initialState" equals "SUBMITTED"
When I POST a REST request to URL "/sellerkyc" with payload
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

Scenario: Retrieve the sellerkyc that just got created
When I GET a REST request to URL "/sellerkyc/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the review event to the sellerkyc with comments
Given that "comment" equals "Comment for review"
And that "event" equals "review"
When I PATCH a REST request to URL "/sellerkyc/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "UNDER_REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the reject event to the sellerkyc with comments
Given that "comment" equals "Comment for reject"
And that "event" equals "reject"
When I PATCH a REST request to URL "/sellerkyc/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REJECTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the sellerkyc Workflow Service using a REST client. Sellerkyc service exists and is under test.
It helps to create a sellerkyc and manages the state of the sellerkyc as documented in states xml
Scenario: Create a new sellerkyc
Given that "flowName" equals "sellerKycFlow"
And that "initialState" equals "SUBMITTED"
When I POST a REST request to URL "/sellerkyc" with payload
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

Scenario: Retrieve the sellerkyc that just got created
When I GET a REST request to URL "/sellerkyc/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the review event to the sellerkyc with comments
Given that "comment" equals "Comment for review"
And that "event" equals "review"
When I PATCH a REST request to URL "/sellerkyc/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "UNDER_REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the requestInfo event to the sellerkyc with comments
Given that "comment" equals "Comment for requestInfo"
And that "event" equals "requestInfo"
When I PATCH a REST request to URL "/sellerkyc/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "INFO_REQUESTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
