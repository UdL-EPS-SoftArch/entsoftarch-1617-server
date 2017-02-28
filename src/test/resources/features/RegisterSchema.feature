Feature: Register Schema
  In order to share or sell schema
  I want to register a schema and make it available

  Scenario: Register a schema title and description
    Given I login as "owner" with password "password"
    And There are 0 schemas registered
    When I register a schema with title "my title" and description "my own schema"
    Then The new schema has title "my title" and description "my own schema"
    And There is 1 schema registered

  Scenario: Register a new schema if one already registered
    Given I login as "owner" with password "password"
    And There is a schema with title "my original title" and description "my original schema" and owner "owner"
    And There are 1 schema registered
    When I register a schema with title "my own title" and description "my own schema"
    Then There are 2 schemas registered


  Scenario: Register a schema but wrong password
    Given I login as "owner" with password "wrongpassword"
    And There are 0 schemas registered
    When I register a schema with title "my title" and description "my own schema"
    Then The response code is 401
    And The error message is "Bad credentials"
    And There are 0 schemas registered
