Feature: Register Dataset
  In order to share or sell owned data
  As a data owner
  I want to register a dataset and make it available

  Scenario: Register a dataset description without data
    Given I login as "owner" with password "password"
    And There are 0 datasets registered
    When I register a dataset with title "my own data"
    Then The new dataset has title "my own data"
    And The new dataset has date and time approximately now
    And There is 1 dataset registered
    And User "owner" owns 1 dataset

  Scenario: Register a new dataset if one already registered
    Given I login as "owner" with password "password"
    And There is a dataset with title "my original data" and owner "owner"
    And There are 1 datasets registered
    When I register a dataset with title "my own data"
    Then There are 2 datasets registered

  Scenario: Register a dataset but wrong password
    Given I login as "owner" with password "wrongpassword"
    And There are 0 datasets registered
    When I register a dataset with title "my own data"
    Then The response code is 401
    And The error message is "Bad credentials"
    And There are 0 datasets registered

  Scenario: Register a dataset but empty title
    Given I login as "owner" with password "password"
    When I register a dataset with title ""
    Then The response code is 400
    And The error message is "may not be empty"
    And There are 0 datasets registered