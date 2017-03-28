Feature: Delete field on schema
  I have a schema with fields, and I want remove one of them.

  Scenario: I want delete field from schema
    Given I login as "owner" with password "password"
    And There is a schema with title "my own schema" and owner "owner"
    And There are 0 fields added
    When I add a field with title "my own field" and description "my own field description" to schema "my own schema"
    Then There is 1 field added
    And The schema with title "my own schema" has 1 field
    And The schema with title "my own schema" has a field with title "my own field"
    When I delete the field with title "my own field"
    Then The response code is 204
    And There are 0 field added