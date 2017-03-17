Feature: Add Schema Field
  In order to define the structure of a schema
  As a data owner
  I want to add a new field to a schema.

  Scenario: Add a first field to a schema
    Given I login as "owner" with password "password"
    And There is a schema with title "my own schema" and owner "owner"
    And There are 0 fields added
    When I add a field with title "my own field" and description "my own field description" to schema "my own schema"
    Then There is 1 field added
    And The schema with title "my own schema" has 1 field
    And The schema with title "my own schema" has a field with title "my own field"

  Scenario: Add another field to a schema
    # TODO

  Scenario: Add an existing field to a schema
    Given I login as "owner" with password "password"
    And There is a schema with title "my own schema" and owner "owner"
    And There is a field with title "my own field" in schema "my own schema"
    And There are 1 field added
    When I add a field with title "my own field" and description "my own field description" to schema "my own schema"
    Then The response code is 409
    And The error message is "FIELD_TITLE_UNIQUE_PER_SCHEMA"
    # The field is created
    And There are 2 fields added
    # But it is not associated to the schema
    And The schema with title "my own schema" has 1 fields
    And The schema with title "my own schema" has a field with title "my own field"

  Scenario: Add two fields with same title to different schemas
    #TODO

  Scenario: Add a field to the schema but wrong password
    Given I login as "owner" with password "wrongpassword"
    And There is a schema with title "my own schema" and owner "owner"
    When I add a field with title "my own field" and description "my own field description" to schema "my own schema"
    Then The response code is 401
    And The error message is "Bad credentials"
    And There are 0 fields added 

