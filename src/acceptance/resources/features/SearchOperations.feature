Feature: Search for objects
  Search for a record by any field

  Scenario: Search for an object by name
    Given the application is alive
    And the data is cleared
    And two sample objects are created
    When a search of "object1" is requested
    Then the object is found
    And the two sample objects can be deleted

  Scenario: Search for an object by number
    Given the application is alive
    And the data is cleared
    And two sample objects are created
    When a search of "12" is requested
    Then two sample objects are found
    And the two sample objects can be deleted

  Scenario: Search for an object not found
    Given the application is alive
    And the data is cleared
    And two sample objects are created
    When a search of "ooo" is requested
    Then no objects are found
    And the two sample objects can be deleted