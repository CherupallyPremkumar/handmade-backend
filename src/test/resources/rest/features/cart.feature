Feature: Tests the Cart Query Service using a REST client.

  Scenario: Cart - Test Contains with an array and sort descending
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I POST a REST request to URL "/q/cart" with payload
"""
{
	"filters" :{
	"tenant_id":"tenant0",
	"id":"32caf83a-4fe1-44cd-a100-67158ecef2ed",
	"location_id": "1"
	}
}
"""
    Then the http status code is 200
    And the top level code is 200
    And success is true
    And the REST response key "numRowsReturned" is "1"
    And the REST response key "list[0].row.id" is "32caf83a-4fe1-44cd-a100-67158ecef2ed"
    And the REST response key "list[0].row.id" is "32caf83a-4fe1-44cd-a100-67158ecef2ed"

  Scenario: Cart - Test Contains with an array and sort descending
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I POST a REST request to URL "/q/cart" with payload
"""
{
	"filters" :{
	"tenant_id":"tenant0",
	"id":"32caf83a-4fe1-44cd-a100-67158ecef2ed",
	"location_id": "1"
	}
}
"""
    Then the http status code is 200
    And the top level code is 200
    And success is true
    And the REST response key "numRowsReturned" is "1"
    And the REST response key "list[0].row.id" is "32caf83a-4fe1-44cd-a100-67158ecef2ed"
    And the REST response key "list[0].row.id" is "32caf83a-4fe1-44cd-a100-67158ecef2ed"