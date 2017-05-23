Feature: Update Dataset
  In order to change my owned data
  As a data owner
  I want to update a dataset

  Scenario: Update a dataset title
    Given I login as "owner" with password "password"
    And There is a dataset with title "my original data" and owner "owner"
    And There is 1 dataset registered
    When I update my dataset with title "my original data" to title "my changed title"
    Then The new dataset has title "my changed title"
    And The new dataset has lastModified field approximately now
    And There is 1 dataset registered


  Scenario: Update a dataset description
    Given I login as "owner" with password "password"
    And There is a dataset with description "my original data description" and owner "owner"
    And There is 1 dataset registered
    When I update my dataset with description "my original data description" to description "my changed description"
    Then The new dataset has description "my changed description"
    And The new dataset has lastModified field approximately now
    And There is 1 dataset registered

  Scenario: Update a dataset but not owner
    Given I login as "user" with password "password"
    And There is a dataset with title "my original data" and owner "owner"
    And There is 1 datasets registered
    When I update my dataset with title "my original data" to title "my changed title"
    Then The response code is 403
    And The error message is "Access is denied"
    And The dataset has title "my original data"

  Scenario: Update a dataset schema
    Given I login as "owner" with password "password"
    And There is a schema with title "my schema" and owner "owner"
    And There is a schema with title "my new schema" and owner "owner"
    And There is a dataset with title "my dataset" and owner "owner"
    And I set a schema with title "my schema" to dataset with title "my dataset"
    And The dataset with title "my dataset" has a schema with title "my schema"
    When I update my dataset with title "my dataset" to schema "my new schema"
    Then The response code is 200
    And The dataset with title "my dataset" has a schema with title "my new schema"