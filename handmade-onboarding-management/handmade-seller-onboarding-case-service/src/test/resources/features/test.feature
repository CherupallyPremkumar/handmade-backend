Feature: Testcase ID 
Tests the selleronboardingcase Workflow Service using a REST client. Selleronboardingcase service exists and is under test.
It helps to create a selleronboardingcase and manages the state of the selleronboardingcase as documented in states xml

Scenario: Create a new selleronboardingcase
Given that "flowName" equals "sellerOnboardingCaseFlow"
And that "initialState" equals "INITIATED"
When I POST a REST request to URL "/selleronboardingcase" with payload
"""json
{
    "sellerId": "seller-001",
    "email": "seller1@example.com",
    "businessName": "Handmade Treasures",
    "startedAt": "2026-01-19T10:00:00Z"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.businessName" is "Handmade Treasures"

Scenario: Retrieve the selleronboardingcase that just got created
When I GET a REST request to URL "/selleronboardingcase/${id}"
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the start event to the selleronboardingcase
Given that "event" equals "start"
When I PATCH a REST request to URL "/selleronboardingcase/${id}/${event}" with payload
"""json
{
    "comment": "Starting onboarding"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_PROGRESS"

Scenario: Send the submit event to the selleronboardingcase
Given that "event" equals "submit"
When I PATCH a REST request to URL "/selleronboardingcase/${id}/${event}" with payload
"""json
{
    "comment": "Submitting documents"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "SUBMITTED"

Scenario: Send the review event to the selleronboardingcase
Given that "event" equals "review"
When I PATCH a REST request to URL "/selleronboardingcase/${id}/${event}" with payload
"""json
{
    "comment": "Reviewing application"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "UNDER_REVIEW"

Scenario: Send the approve event to the selleronboardingcase
Given that "event" equals "approve"
When I PATCH a REST request to URL "/selleronboardingcase/${id}/${event}" with payload
"""json
{
    "comment": "Approving application"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"

Scenario: Send the activate event to the selleronboardingcase
Given that "event" equals "activate"
When I PATCH a REST request to URL "/selleronboardingcase/${id}/${event}" with payload
"""json
{
    "comment": "Activating seller account"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"

Scenario: Create another case for rejection test
Given that "flowName" equals "sellerOnboardingCaseFlow"
And that "initialState" equals "INITIATED"
When I POST a REST request to URL "/selleronboardingcase" with payload
"""json
{
    "sellerId": "seller-002",
    "email": "seller2@example.com",
    "businessName": "Rejected Works",
    "startedAt": "2026-01-19T11:00:00Z"
}
"""
Then success is true
And store "$.payload.mutatedEntity.id" from response to "id2"

Scenario: Move second case to UNDER_REVIEW
When I PATCH a REST request to URL "/selleronboardingcase/${id2}/start" with payload
"""json
{}
"""
And I PATCH a REST request to URL "/selleronboardingcase/${id2}/submit" with payload
"""json
{}
"""
And I PATCH a REST request to URL "/selleronboardingcase/${id2}/review" with payload
"""json
{}
"""
Then success is true

Scenario: Reject the second case
Given that "event" equals "reject"
When I PATCH a REST request to URL "/selleronboardingcase/${id2}/${event}" with payload
"""json
{
    "comment": "Rejecting due to missing info"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "REJECTED"

Scenario: Move to INFO_REQUESTED test
Given that "flowName" equals "sellerOnboardingCaseFlow"
And that "initialState" equals "INITIATED"
When I POST a REST request to URL "/selleronboardingcase" with payload
"""json
{
    "sellerId": "seller-003",
    "email": "seller3@example.com",
    "businessName": "Info Needed",
    "startedAt": "2026-01-19T12:00:00Z"
}
"""
And store "$.payload.mutatedEntity.id" from response to "id3"
And I PATCH a REST request to URL "/selleronboardingcase/${id3}/start" with payload
"""json
{}
"""
And I PATCH a REST request to URL "/selleronboardingcase/${id3}/submit" with payload
"""json
{}
"""
And I PATCH a REST request to URL "/selleronboardingcase/${id3}/review" with payload
"""json
{}
"""
And I PATCH a REST request to URL "/selleronboardingcase/${id3}/requestInfo" with payload
"""json
{
    "comment": "Need more docs"
}
"""
Then success is true
And the REST response key "mutatedEntity.currentState.stateId" is "INFO_REQUESTED"

Scenario: Provide info and move back to UNDER_REVIEW
When I PATCH a REST request to URL "/selleronboardingcase/${id3}/provideInfo" with payload
"""json
{
    "comment": "Here are the docs"
}
"""
Then success is true
And the REST response key "mutatedEntity.currentState.stateId" is "UNDER_REVIEW"
