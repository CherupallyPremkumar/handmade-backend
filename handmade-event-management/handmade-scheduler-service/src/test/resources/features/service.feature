Feature: Tests the scheduler Service using a REST client.
 
  Scenario: Save the scheduler first.
    Given that "tenant" equals "tenant0"
    And that "employee" equals "E1"
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I construct a REST request with header "x-chenile-eid" and value "${employee}"
    And I POST a REST request to URL "/scheduler" with payload
    """json
    {
      "jobName": "OrderCleanupJob",
      "cronExpression": "0 0 * * * ?",
      "status": "ACTIVE"
    }
    """
    Then success is true
    And the REST response contains key "id"
    And store "$.payload.id" from response to "id"

  Scenario: Retrieve the saved scheduler
    Given that "entity" equals "scheduler"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I GET a REST request to URL "/${entity}/${id}"
    Then success is true
    And the REST response key "id" is "${id}"
    And the REST response key "jobName" is "OrderCleanupJob"

  Scenario: Save a scheduler using an ID that already is determined
    Given that "customId" equals "job-123"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I construct a REST request with header "x-chenile-eid" and value "E1"
    And I POST a REST request to URL "/scheduler" with payload
    """json
    {
      "id": "${customId}",
      "jobName": "DailyReportJob",
      "cronExpression": "0 0 0 * * ?",
      "status": "ACTIVE"
    }
    """
    Then success is true
    And the REST response key "id" is "${customId}"
    And the REST response key "jobName" is "DailyReportJob"

  Scenario: Retrieve the saved scheduler with custom ID
    Given that "entity" equals "scheduler"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I GET a REST request to URL "/${entity}/job-123"
    Then success is true
    And the REST response key "id" is "job-123"
    And the REST response key "jobName" is "DailyReportJob"