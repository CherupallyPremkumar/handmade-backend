Feature: Tests the cmsentry Workflow Service using a REST client. This is done only for the
first testcase. Cmsentry service exists and is under test.
It helps to create a cmsentry and manages the state of the cmsentry as documented in states xml
Scenario: Create a new cmsentry
Given that "flowName" equals "cmsEntryFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/cmsentry" with payload
"""json
{
    "schemaId": "test-schema-001",
    "slug": "test-cms-entry-001",
    "title": "Test CMS Entry Title"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.title" is "Test CMS Entry Title"

Scenario: Retrieve the cmsentry that just got created
When I GET a REST request to URL "/cmsentry/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the publish event to the cmsentry with comments
 Given that "comment" equals "Comment for publish"
 And that "event" equals "publish"
When I PATCH a REST request to URL "/cmsentry/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PUBLISHED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to cmsentry . This will err out.
When I PATCH a REST request to URL "/cmsentry/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

