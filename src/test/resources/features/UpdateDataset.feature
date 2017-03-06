Feature: Update Dataset
  In order to change my owned data
  As a data owner
  I want to update a dataset

  Scenario: Update a dataset description
    Given I login as "owner" with password "password"
    And There is a dataset with description "my original data" and owner "owner"
    And There is 1 dataset registered
    When I update my dataset with desciption "my original data" to description "my changed description"
    Then The new dataset has description "my changed description"
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
