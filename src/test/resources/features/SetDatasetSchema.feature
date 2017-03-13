Feature: Set Dataset Schema
  I want to set a schema to datset

  Scenario: Set a schema to dataset by title
    Given I login as "owner" with password "password"
    And There are 0 schemas registered
    And There are 0 datasets registered
    When I register a schema with title "my schema" and description "my own schema"
    Then The new schema has title "my schema" and description "my own schema"
    And There is 1 schema registered
    When I register a dataset with title "my dataset"
    Then The new dataset has title "my dataset"
    And There is 1 dataset registered
    When I set a schema with title "my schema" to dataset with title "my dataset"
    Then The dataset with title "my dataset" has a schema with title "my schema"

