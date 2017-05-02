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