Feature: Testcase ID 
Tests the sellerstore Workflow Service using a REST client. Sellerstore service exists and is under test.
It helps to create a sellerstore and manages the state of the sellerstore as documented in states xml

Scenario: Create a new sellerstore
Given that "flowName" equals "HM_SELLER_STORE_FLOW"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/sellerstore" with payload
"""json
{
    "platformId": "platform-001",
    "sellerAccountId": "seller-acct-001",
    "sellerCode": "STORE001",
    "sellerName": "Test Seller Store",
    "displayName": "Test Store Display",
    "urlPath": "/store/test-store",
    "currency": "USD"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.sellerCode" is "STORE001"

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

Scenario: Send the unpublish event to the sellerstore with comments
Given that "comment" equals "Comment for unpublish"
And that "event" equals "unpublish"
When I PATCH a REST request to URL "/sellerstore/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "UNPUBLISHED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
