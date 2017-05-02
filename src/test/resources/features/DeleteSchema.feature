Feature: Delete Schema
  In order to stop sharing or selling owned data
  As a data owner
  I want to deregister a schema and make it unavailable

  Scenario: Delete owned schema without fields
    Given There is a schema with title "schema title1" and description "schema owned by owner" and owner "owner"
    And There is 1 schema registered
    And I login as "owner" with password "password"
    When I delete the schema with title "schema title1"
    Then The response code is 204
    And There are 0 schema registered

  Scenario: I want delete schema with one field
    Given I login as "owner" with password "password"
    And There is a schema with title "my own schema" and owner "owner"
    And There are 0 fields added
    When I add a field with title "my own field" and description "my own field description" to schema "my own schema"
    Then There is 1 field added
    And The schema with title "my own schema" has 1 field
    And The schema with title "my own schema" has a field with title "my own field"
    When I delete the schema with title "my own schema"
    Then The response code is 409
    And There are 1 schema registered
    And There are 1 field added

  Scenario: I want delete schema with one field deleted
    Given I login as "owner" with password "password"
    And There is a schema with title "my own schema" and owner "owner"
    And There are 0 fields added
    When I add a field with title "my own field" and description "my own field description" to schema "my own schema"
    Then There is 1 field added
    And The schema with title "my own schema" has 1 field
    And The schema with title "my own schema" has a field with title "my own field"
    When I delete the field with title "my own field"
    When I delete the schema with title "my own schema"
    Then The response code is 204
    And There are 0 schema registered
    And There are 0 field added