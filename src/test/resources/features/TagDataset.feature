Feature: Tag Dataset
  In order to tag a dataset
  As a data owner
  I want to tag a dataset and make it available

  Scenario: Tag a dataset already created by owner
    Given I login as "owner" with password "password"
    And There is a tag with name "tag1"
    And There is a dataset with title "My dataset" and owner "owner"
    When I tag the dataset titled "My dataset" with tag "tag1"
    Then The dataset has tag "tag1"

  Scenario: Tag a dataset with another tag
    Given I login as "owner" with password "password"
    And There is a tag with name "tag1"
    And There is a dataset with title "My dataset", tagged with "tag1" and owner "owner"
    And I create a tag with name "tag2"
    When I tag the dataset titled "My dataset" with tag "tag2"
    Then The dataset with title "My dataset" has 2 tags

  Scenario: Tag dataset but not owner
    Given I login as "user" with password "password"
    And There is a tag with name "tag1"
    And There is a dataset with title "My dataset" and owner "owner"
    When I tag the dataset titled "My dataset" with tag "tag1"
    Then The response code is 403
    And The error message is "Access is denied"
    And The dataset with title "My dataset" has 0 tags

  Scenario: Can't tag a dataset with the same tag
    Given I login as "owner" with password "password"
    And There is a tag with name "tag1"
    And There is a dataset with title "My dataset", tagged with "tag1" and owner "owner"
    When I create a tag with name "tag1"
    Then The response code is 409