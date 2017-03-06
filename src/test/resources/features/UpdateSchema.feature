Feature: Update Schema
  In order to change my owned data
  As a data owner
  I want to update a schema

  Scenario: Update a schema title
    Given I login as "owner" with password "password"
    And There is a schema with title "my original data" and owner "owner"
    And There is 1 schema registered
    When I update my schema with title "my original data" to title "my changed title"
    Then The new schema has title "my changed title"
    And The new schema has lastModified field approximately now
    And There is 1 schema registered


  Scenario: Update a schema description
    Given I login as "owner" with password "password"
    And There is a schema with description "my original data description" and owner "owner"
    And There is 1 schema registered
    When I update my schema with description "my original data description" to description "my changed description"
    Then The new schema has description "my changed description"
    And The new schema has lastModified field approximately now
    And There is 1 schema registered

  Scenario: Update a schema but not owner
    Given I login as "user" with password "password"
    And There is a schema with title "my original data" and owner "owner"
    And There is 1 schemas registered
    When I update my schema with title "my original data" to title "my changed title"
    Then The response code is 403
    And The error message is "Access is denied"
    And The schema has title "my original data"
