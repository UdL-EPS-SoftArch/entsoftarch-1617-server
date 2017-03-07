Feature: SearchTagByName
  In order to list all tags that match with a given name
  As a data user
  I want to show the tags that match with a name

  Scenario: Show a tag given a name
    Given There is a tag with name "tag1"
    And There are 1 tags created
    When I search with "tag1"
    Then Show 1 tag