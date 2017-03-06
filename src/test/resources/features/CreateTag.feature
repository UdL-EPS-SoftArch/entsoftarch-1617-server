Feature: Create Tag
  In order to create tags
  As a data user
  I want to create a tag and make it available

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