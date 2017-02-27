Feature: SearchDatasetByDescription
  In order to list all datasets that match with a given description words
  As a data user
  I want to show the datasets that match with a description

  Scenario: Show a dataset without given a description
    Given There is a dataset with description "dataset owned by owner" and owner "owner"
    When I search with a blank description
    Then Show 1 datasets


