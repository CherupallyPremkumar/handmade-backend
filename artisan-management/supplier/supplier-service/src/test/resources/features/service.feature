Feature: Tests the Chenile Workflow Service using a REST client. Supplier service exists and is under test.
It helps to create a supplier and manages the state of the supplier as follows:
OPENED -(assign) -> ASSIGNED -(resolve) -> RESOLVED -(close) -> CLOSED
 
  Scenario: Test create supplier
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/supplier" with payload
    """
    {
	    "openedBy": "USER1",
	    "description": "Description"
	}
	"""
    Then the REST response contains key "mutatedEntity"
    And store "$.payload.mutatedEntity.id" from  response to "id"
    And the REST response key "mutatedEntity.currentState.stateId" is "OPENED"
    And the REST response key "mutatedEntity.openedBy" is "USER1"
    And the REST response key "mutatedEntity.description" is "Description"
	  
	Scenario: Retrieve the supplier that just got created
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
	  When I GET a REST request to URL "/supplier/${id}" 
	  Then the REST response contains key "mutatedEntity"
	  And the REST response key "mutatedEntity.id" is "${id}"
	  And the REST response key "mutatedEntity.currentState.stateId" is "OPENED"
	  And the REST response key "mutatedEntity.openedBy" is "USER1"
	  
	Scenario: Assign the supplier to an assignee with comments
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
		When I PATCH a REST request to URL "/supplier/${id}/assign" with payload
		"""
		{
			"assignee": "MY-ASSIGNEE",
			"comment": "MY-ASSIGNEE-CAN-FIX-THIS"
		}
		"""
	  Then the REST response contains key "mutatedEntity"
	  And the REST response key "mutatedEntity.id" is "${id}"
	  And the REST response key "mutatedEntity.currentState.stateId" is "ASSIGNED"
	  And the REST response key "mutatedEntity.assignee" is "MY-ASSIGNEE"
	  And the REST response key "mutatedEntity.assignComment" is "MY-ASSIGNEE-CAN-FIX-THIS"
	  
	 Scenario: Resolve the supplier with comments
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
		When I PATCH a REST request to URL "/supplier/${id}/resolve" with payload
		"""
		{
			"comment": "CANNOT-DUPLICATE"
		}
		"""
	  Then the REST response contains key "mutatedEntity"
	  And the REST response key "mutatedEntity.id" is "${id}"
	  And the REST response key "mutatedEntity.currentState.stateId" is "RESOLVED"
	  And the REST response key "mutatedEntity.resolveComment" is "CANNOT-DUPLICATE"

    Scenario: Perform mandatory activity (doRetrospective) on the resolved supplier with comments
    Given that "comment" equals "Retrospective performed successfully."
    And that "event" equals "doRetrospective"
        When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
        And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
        When I PATCH a REST request to URL "/supplier/${id}/${event}" with payload
        """
        {
        "comment": "${comment}"
        }
        """
        Then the REST response contains key "mutatedEntity"
        And the REST response key "mutatedEntity.id" is "${id}"
        And the REST response key "mutatedEntity.currentState.stateId" is "RESOLVED"
        And the REST response key "mutatedEntity.activities" collection has an item with keys and values:
        | key             | value         |
        | activityName    | ${event}      |
        | activityComment | ${comment}    |

    Scenario: Perform mandatory activity (doPerfTesting) on the resolved supplier with comments
    Given that "comment" equals "Perf test performed successfully."
    And that "event" equals "doPerfTesting"
        When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
        And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
        When I PATCH a REST request to URL "/supplier/${id}/${event}" with payload
        """
        {
        "comment": "${comment}"
        }
        """
        Then the REST response contains key "mutatedEntity"
        And the REST response key "mutatedEntity.id" is "${id}"
        And the REST response key "mutatedEntity.currentState.stateId" is "RESOLVED"
        And the REST response key "mutatedEntity.activities" collection has an item with keys and values:
        | key             | value         |
        | activityName    | ${event}      |
        | activityComment | ${comment}    |

    Scenario: Close the supplier with comments
        When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
        And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
        When I PATCH a REST request to URL "/supplier/${id}/close" with payload
        """
        {
        "comment": "OK"
        }
        """
        Then the REST response contains key "mutatedEntity"
        And the REST response key "mutatedEntity.id" is "${id}"
        And the REST response key "mutatedEntity.currentState.stateId" is "CLOSED"
        And the REST response key "mutatedEntity.closeComment" is "OK"

    Scenario: Add a new mandatory activity verifyLogs for the CLOSED state.
    Send verifyLogs event on the supplier with comments. This will not affect the state of the state entity.
        Given that config strategy is "supplierConfigProvider" with prefix "Supplier"
        And that a new mandatory activity "verifyLogs" is added from state "CLOSED" to state "AreClosedActivitiesComplete" in flow "SupplierFlow"
        And that "comment" equals "All logs have been verified."
        And that "event" equals "verifyLogs"
        When I PATCH a REST request to URL "/supplier/${id}/${event}" with payload
        """json
            {
            "comment": "${comment}"
            }
        """
        Then the REST response contains key "mutatedEntity"
        And the REST response key "mutatedEntity.id" is "${id}"
        And the REST response key "mutatedEntity.currentState.stateId" is "CLOSED"
        And the REST response key "mutatedEntity.activities" collection has an item with keys and values:
        | key             | value         |
        | activityName    | ${event}      |
        | activityComment | ${comment}    |

    Scenario: Perform mandatory activity (captureObservations) on the closed supplier with comments
        Given that "comment" equals "Captured all observations successfully."
        And that "event" equals "captureObservations"
        When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
        And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
        When I PATCH a REST request to URL "/supplier/${id}/${event}" with payload
        """
            {
            "comment": "${comment}"
            }
        """
        Then the REST response contains key "mutatedEntity"
        And the REST response key "mutatedEntity.id" is "${id}"
        And the REST response key "mutatedEntity.currentState.stateId" is "ARCHIVED"
        And the REST response key "mutatedEntity.activities" collection has an item with keys and values:
        | key             | value         |
        | activityName    | ${event}      |
        | activityComment | ${comment}    |

Scenario: Assign a closed supplier to someone. This will err out.
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
		When I PATCH a REST request to URL "/supplier/${id}/assign" with payload
		"""
		{
			"assignee": "MY-ASSIGNEE",
			"comment": "MY-ASSIGNEE-CAN-FIX-THIS"
		}
		"""
		Then the REST response does not contain key "mutatedEntity"
		And the http status code is 422

	  