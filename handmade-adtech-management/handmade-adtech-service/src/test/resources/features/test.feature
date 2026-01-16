Feature: Testcase ID 
Tests the adCampaign Workflow Service using a REST client. Adtech service exists and is under test.
It helps to create a adCampaign and manages the state of the adCampaign as documented in states xml
Scenario: Create a new adCampaign
Given that "flowName" equals "adCampaignFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/adCampaign" with payload
"""json
{
    "platformId": "platform-123",
    "sellerId": "seller-456",
    "campaignName": "Test Campaign",
    "startDate": "2023-01-01",
    "budgetAmount": 100.00,
    "budgetType": "DAILY"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.campaignName" is "Test Campaign"

Scenario: Retrieve the adCampaign that just got created
When I GET a REST request to URL "/adCampaign/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the launch event to the adCampaign with comments
Given that "comment" equals "Comment for launch"
And that "event" equals "launch"
When I PATCH a REST request to URL "/adCampaign/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the pause event to the adCampaign with comments
Given that "comment" equals "Comment for pause"
And that "event" equals "pause"
When I PATCH a REST request to URL "/adCampaign/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PAUSED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
