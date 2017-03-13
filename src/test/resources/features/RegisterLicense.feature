Feature: Register License
  In order to define a license
  As a data owner
  I want to register a license and make it available

  Scenario: Register a open license with text
    Given I login as "owner" with password "password"
    And There are 0 open licenses registered
    When I register a open license with text "text"
    Then The new license has text "text"
    And There is 1 open license registered
    And User "owner" owns 1 open license

  Scenario: Register a closed license with text and price
    Given I login as "owner" with password "password"
    And There are 0 closed licenses registered
    When I register a closed license with text "text" and price 10
    Then The new closed license has text "text" and price 10
    And There is 1 closed license registered
    And User "owner" owns 1 closed license

  Scenario: Register a new open license if one already registered
    Given I login as "owner" with password "password"
    And There is a open license with text "text" and owner "owner"
    And There are 1 open license registered
    When I register a open license with text "new text"
    Then There are 2 open license registered

  Scenario: Register a new closed license if one already registered
    Given I login as "owner" with password "password"
    And There is a closed license with text "text" and price 10 and owner "owner"
    And There are 1 closed license registered
    When I register a closed license with text "new text" and price 20
    Then There are 2 closed license registered

  Scenario: Register a open license but wrong password
    Given I login as "owner" with password "wrongpassword"
    And There are 0 open license registered
    When I register a open license with text "text"
    Then The response code is 401
    And The error message is "Bad credentials"
    And There are 0 open license registered

  Scenario: Register a closed license but wrong password
    Given I login as "owner" with password "wrongpassword"
    And There are 0 closed license registered
    When I register a closed license with text "text" and price 10
    Then The response code is 401
    And The error message is "Bad credentials"
    And There are 0 closed license registered