Feature: CRUD and list on an object
  Create, read, update, delete on an arbitrary object.

  Scenario: List test objects
    Given the application is alive
    And the data is cleared
    And two sample objects are created
    When a list of sample objects are requested
    Then two sample objects are found
    And the two sample objects can be deleted

  Scenario: Update test objects
    Given the application is alive
    And the data is cleared
    And two sample objects are created
    When the objects are updated
    Then the objects reflect the updates
    And the two sample objects can be deleted