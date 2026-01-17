Feature: Testcase ID 
Tests the etljob Workflow Service using a REST client. Etljob service exists and is under test.
It helps to create a etljob and manages the state of the etljob as documented in states xml
Scenario: Create a new etljob
Given that "flowName" equals "etlJobFlow"
And that "initialState" equals "SCHEDULED"
When I POST a REST request to URL "/etljob" with payload
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

Scenario: Retrieve the etljob that just got created
When I GET a REST request to URL "/etljob/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the start event to the etljob with comments
Given that "comment" equals "Comment for start"
And that "event" equals "start"
When I PATCH a REST request to URL "/etljob/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RUNNING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the fail event to the etljob with comments
Given that "comment" equals "Comment for fail"
And that "event" equals "fail"
When I PATCH a REST request to URL "/etljob/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FAILED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the etljob Workflow Service using a REST client. Etljob service exists and is under test.
It helps to create a etljob and manages the state of the etljob as documented in states xml
Scenario: Create a new etljob
Given that "flowName" equals "etlJobFlow"
And that "initialState" equals "SCHEDULED"
When I POST a REST request to URL "/etljob" with payload
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

Scenario: Retrieve the etljob that just got created
When I GET a REST request to URL "/etljob/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the start event to the etljob with comments
Given that "comment" equals "Comment for start"
And that "event" equals "start"
When I PATCH a REST request to URL "/etljob/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RUNNING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the complete event to the etljob with comments
Given that "comment" equals "Comment for complete"
And that "event" equals "complete"
When I PATCH a REST request to URL "/etljob/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "COMPLETED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
