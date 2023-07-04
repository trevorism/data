Feature: Aggregate objects
  Get aggregated objects

  Scenario: Aggregate test objects by number and get sum of decimal field
    Given the application is alive
    And the data is cleared
    And two sample objects are created
    When an aggregation request of by number and sum of decimal field is requested
    Then the aggregation result is returned
    And the two sample objects can be deleted