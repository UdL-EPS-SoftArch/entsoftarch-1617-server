Feature: Register Dataset
  In order to share or sell owned data
  As a data owner
  I want to register a dataset and make it available

  Scenario: Register a dataset description without data
    Given I login as "owner" with password "password"
    And There are 0 datasets registered
    When I register a dataset with description "my own data"
    Then There are 1 datasets registered