Feature: SearchDatasetByTag
  In order to list all datasets that have the given tag
  As a data user
  I want to show the datasets that have the given tag

  Scenario: Show a dataset given a tag
    Given There is a tag with name "tag1"
    And There is a dataset with description "dataset1" and owner "owner"
    And There are 1 datasets registered
    And The dataset with title "dataset" is tagged with "tag1"
    When I search dataset with tag "tag1"
    Then Show 1 datasets