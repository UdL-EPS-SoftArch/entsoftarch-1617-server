Feature: Add Schema Field
  In order to define the structure of a schema
  As a data owner
  I want to add a new field in the schema.

  Scenario: Add a new field to the schema
    Given I login as "owner" with password "password"
    And There is a schema with title "my title" and description "my own schema" and owner "owner"
    And There are 0 fields added
    When I add a field with title "My field title" and description "my own field"
    Then The new field has title "My field tittle" and description "my own field"
    And There is 1 field added

  Scenario: Add an existing field to the schema
    Given I login as "owner" with password "password"
    And There is a schema with title "my title" and description "my own schema" and owner "owner"
    And There is a field with title "My field title" and description "my own field"
    When I add a field with title "My field tittle" and description "my own field"
    Then The response code is 500
    And The error message is "Exception: Duplicate field"
    And There are 0 fields added 

  Scenario: Add a field to the schema but wrong password
    Given I login as "owner" with password "wrongpassword"
    And There is a schema with title "my title" and description "my own schema" and owner "owner"
    When I add a field with title "My field title" and description "my own field"
    Then The response code is 401
    And The error message is "Bad credentials"
    And There are 0 fields added 

