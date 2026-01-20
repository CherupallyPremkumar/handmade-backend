Feature: Tests the seo Service using a REST client.

  Scenario: Save the seo first.
    Given that "tenant" equals "tenant0"
    And that "employee" equals "E1"
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I construct a REST request with header "x-chenile-eid" and value "${employee}"
    And I POST a REST request to URL "/metaTag" with payload
    """
    {
      "pageId": "home-page",
      "entityId": "entity-001",
      "entityType": "PRODUCT",
      "metaKey": "TITLE",
      "metaValue": "Handmade Home Page"
	}
	"""
	Then success is true
    And store "$.payload.id" from response to "id"

  Scenario: Retrieve the saved seo
    Given that "entity" equals "metaTag"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I GET a REST request to URL "/${entity}/${id}"
    Then success is true
    And the REST response key "id" is "${id}"
    And the REST response key "pageId" is "home-page"

  Scenario: Save a seo using an ID that already is determined
    Given that "id" equals "123-metaTag-id"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I construct a REST request with header "x-chenile-eid" and value "E1"
    And I POST a REST request to URL "/metaTag" with payload
  """
  {
    "id": "${id}",
    "pageId": "custom-page",
    "metaKey": "DESCRIPTION",
    "metaValue": "Custom meta description"
  }
  """
    Then success is true
    And the REST response key "id" is "${id}"

  Scenario: Retrieve the saved seo
    Given that "entity" equals "metaTag"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I GET a REST request to URL "/${entity}/${id}"
    Then success is true
    And the REST response key "id" is "${id}"
    And the REST response key "pageId" is "custom-page"