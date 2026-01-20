Feature: Tests the shippinglabel Workflow Service using a REST client. This is done only for the
first testcase. Shippinglabel service exists and is under test.
It helps to create a shippinglabel and manages the state of the shippinglabel as documented in states xml
Scenario: Create a new shippinglabel
Given that "flowName" equals "shippingLabelFlow"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/shippinglabel" with payload
"""json
{
    "shipmentId": "shipment-001",
    "carrierId": "carrier-001",
    "trackingNumber": "TRACK-99999",
    "status": "GENERATED"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.trackingNumber" is "TRACK-99999"

Scenario: Retrieve the shippinglabel that just got created
When I GET a REST request to URL "/shippinglabel/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the print event to the shippinglabel with comments
 Given that "comment" equals "Comment for print"
 And that "event" equals "print"
When I PATCH a REST request to URL "/shippinglabel/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PRINTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the void event to the shippinglabel with comments
 Given that "comment" equals "Comment for void"
 And that "event" equals "void"
When I PATCH a REST request to URL "/shippinglabel/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "VOIDED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to shippinglabel . This will err out.
When I PATCH a REST request to URL "/shippinglabel/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422


Scenario: Create another shippinglabel for Use test
Given that "flowName" equals "shippingLabelFlow"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/shippinglabel" with payload
"""json
{
    "shipmentId": "shipment-002",
    "carrierId": "carrier-002",
    "trackingNumber": "TRACK-22222",
    "status": "GENERATED"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "useId"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And the REST response key "mutatedEntity.trackingNumber" is "TRACK-22222"

Scenario: Print the second shippinglabel
When I PATCH a REST request to URL "/shippinglabel/${useId}/print" with payload
"""json
{
    "comment": "Printing for use"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "PRINTED"

Scenario: Use the second shippinglabel
When I PATCH a REST request to URL "/shippinglabel/${useId}/use" with payload
"""json
{
    "comment": "Using label"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "USED"
