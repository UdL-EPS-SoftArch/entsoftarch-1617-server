Feature: Create Tag
  In order to create tags
  As a data user
  I want to create a tag and make it available

  Scenario: Create a tag
    Given I login as "user" with password "password"
    And There are 0 tags registered
    When I register a tag with name "tag1"
    Then The new tag has name "tag1"
    And There are 1 tags registered