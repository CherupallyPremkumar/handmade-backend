Feature: Tests the sellerkyc Workflow Service using a REST client. This is done only for the
first testcase. Sellerkyc service exists and is under test.
It helps to create a sellerkyc and manages the state of the sellerkyc as documented in states xml

Scenario: Create a new sellerkyc
Given that "flowName" equals "sellerKycFlow"
And that "initialState" equals "SUBMITTED"
When I POST a REST request to URL "/sellerkyc" with payload
"""json
{
    "sellerId": "seller-first-001",
    "documentType": "BUSINESS_LICENSE",
    "documentNumber": "BL123456"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.documentType" is "BUSINESS_LICENSE"

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

Scenario: Send the approve event to the sellerkyc with comments
Given that "comment" equals "Comment for approve"
And that "event" equals "approve"
When I PATCH a REST request to URL "/sellerkyc/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send an invalid event to sellerkyc. This will err out.
When I PATCH a REST request to URL "/sellerkyc/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422
