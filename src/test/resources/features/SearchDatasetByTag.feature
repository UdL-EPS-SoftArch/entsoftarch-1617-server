Feature: SearchDatasetByTag
  In order to list all datasets that have the given tag
  As a data user
  I want to show the datasets that have the given tag

  Scenario: Show a dataset given a tag
    Given There is a tag with name "tag1"
    And There is a dataset with title "My dataset", tagged with "tag1" and owner "owner"
    And There are 1 datasets registered
    And The dataset with title "My dataset" has 1 tags
    When I search dataset with tag "tag1"
    Then Show 1 datasets

  Scenario: Show a dataset given the start of a tag's name
    Given There is a tag with name "tag1"
    And There is a dataset with title "My dataset", tagged with "tag1" and owner "owner"
    And There are 1 datasets registered
    And The dataset with title "My dataset" has 1 tags
    When I search dataset with tag name starting with "tag"
    Then Show 1 datasets

  Scenario: Show a dataset given the start of a tag's name 2
    Given There is a tag with name "tag1"
    And There is a dataset with title "My dataset", tagged with "tag1" and owner "owner"
    And There is a dataset with title "My dataset2", tagged with "tag1" and owner "owner"
    And There are 2 datasets registered
    And The dataset with title "My dataset" has 1 tags
    And The dataset with title "My dataset2" has 1 tags
    When I search dataset with tag name starting with "tag"
    Then Show 2 datasets

  Scenario: Show a dataset given non-existent tag
    Given There is a tag with name "tag1"
    And There is a dataset with title "My dataset", tagged with "tag1" and owner "owner"
    And There are 1 datasets registered
    And The dataset with title "My dataset" has 1 tags
    When I search dataset with tag "tag2"
    Then Show 0 datasets