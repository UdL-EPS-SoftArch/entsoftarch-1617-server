Feature: Register License
  In order to define a license
  As a data owner
  I want to register a license and make it available

  Scenario: Register a open license with text
    Given I login as "owner" with password "password"
    And There are 0 open licenses registered
    When I register a license with text "text"
    Then The new license has text "text"
    And There is 1 open license registered

  Scenario: Register a closed license with text and price
    Given I login as "owner" with password "password"
    And There are 0 closed licenses registered
    When I register a closed license with text "text" and price 10
    Then The new closed license has text "text" and price 10
    And There is 1 closed license registered