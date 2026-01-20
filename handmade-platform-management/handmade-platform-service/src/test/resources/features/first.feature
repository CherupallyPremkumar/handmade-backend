Feature: Tests the platform Workflow Service using a REST client. This is done only for the
first testcase. Platform service exists and is under test.
It helps to create a platform and manages the state of the platform as documented in states xml
Scenario: Create a new platform
Given that "flowName" equals "platformFlow"
And that "initialState" equals "DRAFT"
Given that "tenant" equals "tenant0"
And that "employee" equals "E1"
When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
And I construct a REST request with header "x-chenile-eid" and value "${employee}"
And I POST a REST request to URL "/platform" with payload
"""json
{
    "platformCode": "PLAT-US-UNIQUE-03",
    "name": "United States Platform",
    "countryCode": "US",
    "currencyCode": "USD",
    "marketplaceType": "B2C",
    "description": "United States Region Platform",
    "isActive": true
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.platformCode" is "PLAT-US-UNIQUE-03"

Scenario: Retrieve the platform that just got created
When I GET a REST request to URL "/platform/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the submit event to the platform with comments
 Given that "comment" equals "Comment for submit"
 And that "event" equals "submit"
When I PATCH a REST request to URL "/platform/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the approve event to the platform with comments
 Given that "comment" equals "Comment for approve"
 And that "event" equals "approve"
When I PATCH a REST request to URL "/platform/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the activate event to the platform with comments
 Given that "comment" equals "Comment for activate"
 And that "event" equals "activate"
When I PATCH a REST request to URL "/platform/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the suspend event to the platform with comments
 Given that "comment" equals "Comment for suspend"
 And that "event" equals "suspend"
When I PATCH a REST request to URL "/platform/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SUSPENDED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to platform . This will err out.
When I PATCH a REST request to URL "/platform/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

