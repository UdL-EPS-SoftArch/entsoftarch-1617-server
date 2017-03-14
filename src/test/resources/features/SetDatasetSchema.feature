Feature: Set Dataset Schema
  I want to set a schema to datset

  Scenario: Set a schema to dataset by title
    Given I login as "owner" with password "password"
    And There is a dataset with title "my dataset" and owner "owner"
    And There is a schema with title "my schema" and owner "owner"
    When I set a schema with title "my schema" to dataset with title "my dataset"
    Then The dataset with title "my dataset" has a schema with title "my schema"
    And The datasets defined by schema with title "my schema" include one titled "my dataset"

  Scenario: Set another schema to the same dataset
    Given I login as "owner" with password "password"
    And There is a dataset with title "my dataset" and owner "owner"
    And There is a schema with title "my schema" and owner "owner"
    When I set a schema with title "my schema" to dataset with title "my dataset"
    And There is a schema with title "my other schema" and owner "owner"
    When I set a schema with title "my other schema" to dataset with title "my dataset"
    And The datasets defined by schema with title "my other schema" include one titled "my dataset"
    And The schema with title "my schema" don't include a dataset with title "my dataset"

  Scenario: Set two datasets to the same schema
    Given I login as "owner" with password "password"
    And There is a dataset with title "my dataset" and owner "owner"
    And There is a dataset with title "my other dataset" and owner "owner"
    And There is a schema with title "my schema" and owner "owner"
    When I set a schema with title "my schema" to dataset with title "my dataset"
    When I set a schema with title "my schema" to dataset with title "my other dataset"
    And The datasets defined by schema with title "my schema" include one titled "my dataset"
    And The datasets defined by schema with title "my schema" include one titled "my other dataset"
    Then The schema with title "my schema" has 2 datasets registered






