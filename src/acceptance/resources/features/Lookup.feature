Feature: Data Catalog lookups
  In order to use single data source queries, one must provide a valid data locator.

  Scenario:
    Given the application is alive
    And the data is cleared
    And two sample objects are created
    When a single data source request is invoked with a valid lookup locator
    Then objects are found
    And the two sample objects can be deleted

  Scenario:
    Given the application is alive
    When a single data source request is invoked with a valid invalid locator
    Then an error is returned