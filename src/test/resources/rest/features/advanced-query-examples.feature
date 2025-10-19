Feature: Advanced Query Examples - Chenile SearchRequest

  Background:
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    Scenario: Basic query with pagination
    When I POST a REST request to URL "/q/getByCartId" with payload
    """
    {
      "filters": {
        "customer_id": "customer-001"
      }
    }
    """
    Then the http status code is 200
    And success is true

  Scenario: Query with sorting (ascending)
    When I POST a REST request to URL "/q/order" with payload
    """
    {
      "numRowsInPage": 20,
      "pageNum": 1,
      "sortCriteria": [
        {
          "fieldName": "created_time",
          "ascending": true
        }
      ],
      "filters": {
        "tenant_id": "default"
      }
    }
    """
    Then the http status code is 200
    And success is true

  Scenario: Query with multiple sort criteria
    When I POST a REST request to URL "/q/product" with payload
    """
    {
      "numRowsInPage": 10,
      "pageNum": 1,
      "sortCriteria": [
        {
          "fieldName": "category_id",
          "ascending": true
        },
        {
          "fieldName": "name",
          "ascending": false
        }
      ],
      "filters": {
        "tenant_id": "default"
      }
    }
    """
    Then the http status code is 200
    And success is true

  Scenario: Query with specific fields selection
    When I POST a REST request to URL "/q/customer" with payload
    """
    {
      "numRowsInPage": 10,
      "pageNum": 1,
      "fields": ["id", "name", "email", "phone"],
      "filters": {
        "tenant_id": "default"
      }
    }
    """
    Then the http status code is 200
    And success is true

  Scenario: Query with hidden columns
    When I POST a REST request to URL "/q/order" with payload
    """
    {
      "numRowsInPage": 10,
      "pageNum": 1,
      "hiddenColumns": ["password", "internal_notes"],
      "filters": {
        "tenant_id": "default"
      }
    }
    """
    Then the http status code is 200
    And success is true

  Scenario: Query with system filters
    When I POST a REST request to URL "/q/cart" with payload
    """
    {
      "numRowsInPage": 10,
      "pageNum": 1,
      "filters": {
        "tenant_id": "default",
        "customer_id": "customer-001"
      },
      "systemFilters": {
        "active": true,
        "deleted": false
      }
    }
    """
    Then the http status code is 200
    And success is true

  Scenario: Query for to-do list items
    When I POST a REST request to URL "/q/order" with payload
    """
    {
      "numRowsInPage": 10,
      "pageNum": 1,
      "toDoList": true,
      "filters": {
        "tenant_id": "default",
        "status": "PENDING"
      }
    }
    """
    Then the http status code is 200
    And success is true

  Scenario: Save query as canned report
    When I POST a REST request to URL "/q/order" with payload
    """
    {
      "numRowsInPage": 10,
      "pageNum": 1,
      "cannedReportName": "pending-orders-report",
      "saveChangesToCannedReport": true,
      "publishCannedReportToEveryone": false,
      "filters": {
        "tenant_id": "default",
        "status": "PENDING"
      }
    }
    """
    Then the http status code is 200
    And success is true

  Scenario: Use saved canned report
    When I POST a REST request to URL "/q/order" with payload
    """
    {
      "cannedReportName": "pending-orders-report"
    }
    """
    Then the http status code is 200
    And success is true

  Scenario: Query with OR operation (for elastic search)
    When I POST a REST request to URL "/q/product" with payload
    """
    {
      "numRowsInPage": 10,
      "pageNum": 1,
      "isOrOperation": true,
      "filters": {
        "tenant_id": "default",
        "category_id": "category-001"
      }
    }
    """
    Then the http status code is 200
    And success is true

  Scenario: Complex query with all options
    When I POST a REST request to URL "/q/order" with payload
    """
    {
      "numRowsInPage": 25,
      "pageNum": 2,
      "sortCriteria": [
        {
          "fieldName": "created_time",
          "ascending": false
        },
        {
          "fieldName": "total",
          "ascending": true
        }
      ],
      "fields": ["id", "order_number", "customer_name", "total", "status", "created_time"],
      "hiddenColumns": ["internal_notes", "payment_details"],
      "filters": {
        "tenant_id": "default",
        "status": "DELIVERED",
        "created_time_from": "2024-01-01T00:00:00",
        "created_time_to": "2024-12-31T23:59:59"
      },
      "systemFilters": {
        "deleted": false
      }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "numRowsReturned" is greater than "0"
