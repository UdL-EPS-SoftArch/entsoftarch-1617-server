Feature: Delete Schema
  In order to stop sharing or selling owned data
  As a data owner
  I want to deregister a schema and make it unavailable

  Scenario: Delete owned schema
    Given There is a schema with title "schema title1" and description "schema owned by owner" and owner "owner"
    And There is 1 schema registered
    And I login as "owner" with password "password"
    When I delete the schema with title "schema title1"
    Then The response code is 204
    And There are 0 schema registered

  Scenario: Delete unowned dataset
    Given There is a dataset with title "dataset owned by owner" and owner "owner"
    And I login as "user" with password "password"
    When I delete the dataset with title "dataset owned by owner"
    Then The response code is 403
    And The error message is "Access is denied"