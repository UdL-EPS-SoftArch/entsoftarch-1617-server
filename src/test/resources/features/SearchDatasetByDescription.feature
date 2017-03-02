Feature: SearchDatasetByDescription
  In order to list all datasets that match with a given description words
  As a data user
  I want to show the datasets that match with a description

  Scenario: Show a dataset given a description
    Given There is a dataset with description "dataset owned by owner" and owner "owner"
    And There is 1 datasets registered
    And I login as "owner" with password "password"
    When I search with "by"
    Then Show 1 datasets

  Scenario: Show a dataset given a erroneous description
    Given There is a dataset with description "dataset owned by owner" and owner "owner"
    And There is 1 datasets registered
    And I login as "owner" with password "password"
    When I search with "notmatch"
    Then Show 0 datasets
