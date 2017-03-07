Feature: Tag Dataset
  In order to tag a dataset
  As a data owner
  I want to tag a dataset and make it available

  Scenario: Create a tag
    Given I login as "user" with password "password"
    And There are 0 tags created
    When I create a tag with name "tag1"
    Then The new tag has name "tag1"
    And There are 1 tags created

  Scenario: Create a tag but wrong password
    Given I login as "user" with password "wrongpassword"
    And There are 0 tags created
    When I create a tag with name "tag1"
    Then The response code is 401
    And The error message is "Bad credentials"
    And There are 0 tags created

  Scenario: Create a new tag if one already created
    Given I login as "user" with password "password"
    And There is a tag with name "tag1"
    And There are 1 tags created
    When I create a tag with name "tag2"
    Then There are 2 tags created

  Scenario: Can't create two tags with the same name
    Given I login as "user" with password "password"
    And There is a tag with name "tag1"
    When I create a tag with name "tag2"
    And I create a tag with name "tag1"
    Then The response code is 409