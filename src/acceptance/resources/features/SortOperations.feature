Feature: Sort objects
  Get a list of sorted objects

  Scenario: Sort test objects by name descending
    Given the application is alive
    And two sample objects are created
    When a list of sorted sample objects are requested by name descending
    Then two sample objects are found in order
    And the two sample objects can be deleted