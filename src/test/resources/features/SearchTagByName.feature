Feature: SearchTagByName
  In order to list all tags that match with a given name
  As a data user
  I want to show the tags that match with a name

  Scenario: Show a tag given a name
    Given There is a tag with name "tag1"
    And There is a tag with name "tag2"
    And There are 2 tags created
    When I search tag with name "tag1"
    Then Show 1 tags

  Scenario: Show a tag given a part of a name
    Given There is a tag with name "tag1"
    And There is a tag with name "tag2"
    And There are 2 tags created
    When I search tag with name containing "1"
    Then Show 1 tags

  Scenario: Show a tag given a part of a name 2
    Given There is a tag with name "tag1"
    And There is a tag with name "tag2"
    And There are 2 tags created
    When I search tag with name containing "tag"
    Then Show 2 tags

  Scenario: Show a tag given a non-existent name
    Given There is a tag with name "tag1"
    And There are 1 tags created
    When I search tag with name "tag2"
    Then Show 0 tags