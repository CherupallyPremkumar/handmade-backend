Feature: Testcase ID 
Tests the platform Workflow Service using a REST client. Platform service exists and is under test.
It helps to create a platform and manages the state of the platform as documented in states xml
Scenario: Create a new platform
Given that "flowName" equals "PLATFORM_FLOW"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/platform" with payload
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

Scenario: Retrieve the platform that just got created
When I GET a REST request to URL "/platform/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the activatePlatform event to the platform with comments
Given that "comment" equals "Comment for activatePlatform"
And that "event" equals "activatePlatform"
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
Scenario: Send the enableReadOnly event to the platform with comments
Given that "comment" equals "Comment for enableReadOnly"
And that "event" equals "enableReadOnly"
When I PATCH a REST request to URL "/platform/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "READ_ONLY"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the platform Workflow Service using a REST client. Platform service exists and is under test.
It helps to create a platform and manages the state of the platform as documented in states xml
Scenario: Create a new platform
Given that "flowName" equals "PLATFORM_FLOW"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/platform" with payload
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

Scenario: Retrieve the platform that just got created
When I GET a REST request to URL "/platform/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the activatePlatform event to the platform with comments
Given that "comment" equals "Comment for activatePlatform"
And that "event" equals "activatePlatform"
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
Scenario: Send the decommission event to the platform with comments
Given that "comment" equals "Comment for decommission"
And that "event" equals "decommission"
When I PATCH a REST request to URL "/platform/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DECOMMISSIONED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the platform Workflow Service using a REST client. Platform service exists and is under test.
It helps to create a platform and manages the state of the platform as documented in states xml
Scenario: Create a new platform
Given that "flowName" equals "PLATFORM_FLOW"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/platform" with payload
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

Scenario: Retrieve the platform that just got created
When I GET a REST request to URL "/platform/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the decommission event to the platform with comments
Given that "comment" equals "Comment for decommission"
And that "event" equals "decommission"
When I PATCH a REST request to URL "/platform/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DECOMMISSIONED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
