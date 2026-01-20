Feature: Testcase ID 
Tests the userrole Workflow Service using a REST client. Userrole service exists and is under test.
It helps to create a userrole and manages the state of the userrole as documented in states xml

Scenario: Create a new userrole
Given that "flowName" equals "userRoleFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/userrole" with payload
"""json
{
    "userId": "user-001",
    "roleId": "role-admin",
    "assignedAt": "2024-01-01T00:00:00.000+00:00",
    "assignedBy": "system"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.userId" is "user-001"

Scenario: Retrieve the userrole that just got created
When I GET a REST request to URL "/userrole/${id}"
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the activate event to the userrole
Given that "event" equals "activate"
When I PATCH a REST request to URL "/userrole/${id}/${event}" with payload
"""json
{
    "comment": "Activating role assignment"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"

Scenario: Send the suspend event to the userrole
Given that "event" equals "suspend"
When I PATCH a REST request to URL "/userrole/${id}/${event}" with payload
"""json
{
    "comment": "Temporarily suspending role"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "SUSPENDED"

Scenario: Reactivate the userrole
Given that "event" equals "reactivate"
When I PATCH a REST request to URL "/userrole/${id}/${event}" with payload
"""json
{
    "comment": "Reactivating role"
}
"""
Then success is true
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"

Scenario: Create another for rejection test
Given that "flowName" equals "userRoleFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/userrole" with payload
"""json
{
    "userId": "user-002",
    "roleId": "role-editor",
    "assignedAt": "2024-01-01T00:00:00.000+00:00"
}
"""
Then success is true
And store "$.payload.mutatedEntity.id" from response to "id2"

Scenario: Reject the userrole
When I PATCH a REST request to URL "/userrole/${id2}/reject" with payload
"""json
{
    "comment": "Unauthorized request"
}
"""
Then success is true
And the REST response key "mutatedEntity.currentState.stateId" is "REJECTED"

Scenario: Revoke the active userrole
When I PATCH a REST request to URL "/userrole/${id}/revoke" with payload
"""json
{
    "comment": "Role no longer needed"
}
"""
Then success is true
And the REST response key "mutatedEntity.currentState.stateId" is "REVOKED"
