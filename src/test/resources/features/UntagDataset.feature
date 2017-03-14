Feature: Untag Dataset
  In order to untag a dataset
  As a data owner
  I want to untag a dataset and make it available

  Scenario: Untag a dataset
    Given I login as "owner" with password "password"
    And There is a tag with name "tag1"
    And There is a dataset with title "My dataset", tagged with "tag1" and owner "owner"
    When I remove the tag "tag1" from the dataset titled "My dataset"
    Then The dataset with title "My dataset" has 0 tags

  Scenario: Untag a dataset with more than one tag
    Given I login as "owner" with password "password"
    And There is a tag with name "tag1"
    And There is a tag with name "tag2"
    And There is a dataset with title "My dataset", tagged with "tag1" and owner "owner"
    And I tag the dataset titled "My dataset" with tag "tag2"
    When I remove the tag "tag1" from the dataset titled "My dataset"
    Then The dataset with title "My dataset" has 1 tags
    And The dataset with title "My dataset" is tagged with tag "tag2"

  Scenario: Untag dataset but not owner
    Given I login as "user" with password "password"
    And There is a tag with name "tag1"
    And There is a dataset with title "My dataset", tagged with "tag1" and owner "owner"
    When I remove the tag "tag1" from the dataset titled "My dataset"
    Then The response code is 403
    And The error message is "Access is denied"
    And The dataset with title "My dataset" has 1 tags

  Scenario: Untagging a tag which tags more than one dataset
    Given I login as "owner" with password "password"
    And There is a tag with name "tag1"
    And There is a dataset with title "My dataset", tagged with "tag1" and owner "owner"
    And There is a dataset with title "My second dataset", tagged with "tag1" and owner "owner"
    When I remove the tag "tag1" from the dataset titled "My dataset"
    Then The dataset with title "My dataset" has 0 tags
    And The dataset with title "My second dataset" is tagged with tag "tag1"
    And The dataset with title "My second dataset" has 1 tags


