Feature: Tests the userrole Workflow Service using a REST client. This is done only for the
first testcase. Userrole service exists and is under test.
It helps to create a userrole and manages the state of the userrole as documented in states xml
Scenario: Create a new userrole
Given that "flowName" equals "userRoleFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/userrole" with payload
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

Scenario: Retrieve the userrole that just got created
When I GET a REST request to URL "/userrole/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the reject event to the userrole with comments
 Given that "comment" equals "Comment for reject"
 And that "event" equals "reject"
When I PATCH a REST request to URL "/userrole/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REJECTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to userrole . This will err out.
When I PATCH a REST request to URL "/userrole/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

