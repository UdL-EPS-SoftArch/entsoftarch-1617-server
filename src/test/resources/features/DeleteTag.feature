Feature: Delete Tag
  In order to delete a Tag
  As an admin
  I want to delete a tag

  Scenario: Delete tag
    Given I login as "admin" with password "password"
    And There is a tag with name "tag1"
    Then There are 1 tags created
    When I delete the tag with name "tag1"
    Then There are 0 tags created

  Scenario: Delete tag not being admin
    Given I login as "user" with password "password"
    And There is a tag with name "tag1"
    Then There are 1 tags created
    When I delete the tag with name "tag1"
    Then The response code is 403
    And The error message is "Access is denied"
    And There are 1 tags created

  Scenario: Delete tag with dataset
    Given I login as "admin" with password "password"
    And There is a tag with name "tag1"
    Then There are 1 tags created
    And There is a dataset with title "My dataset", tagged with "tag1" and owner "owner"
    When I delete the tag with name "tag1"
    Then There are 0 tags created
    And The dataset with title "My dataset" has 0 tags