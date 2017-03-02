Feature: Register License
  In order to define a license
  As a data owner
  I want to register a license and make it available

Scenario: Register a open license text
  Given I login as "owner" with password "password"
  And There are 0 licenses registered
  When I register a license with text "text"
  Then The new license has text "text"
  And There is 1 license registered

"""Scenario: Register a closed license text and price
  Given I login as "owner" with password "password"
  And There are 0 licenses registered
  When I register a license with text "text" and price "price"
  Then The new license has text "text" and price "price"
  And There is 1 license registered

Scenario: Register a new open license if already registered
  Given I login as "owner" with password "password"
  And There is a license with text "my original text" and owner "owner"
  And There are 1 license registered
  When I register a license with text "text"
  Then There are 1 licenses registered

Scenario: Register a new closed license if already registered
  Given I login as "owner" with password "password"
  And There is a license with text "my original text" and price "my original price" and owner "owner"
  And There are 1 license registered
  When I register a license with text "my own text" and price "my own price"
  Then There are 1 license registered

Scenario: Register a open license but wrong password
  Given I login as "owner" with password "wrongpassword"
  And There are 0 licenses registered
  When I register a license with text "text"
  Then The response code is 401
  And The error message is "Bad credentials"
  And There are 0 licenses registered

Scenario: Register a closed license but wrong password
  Given I login as "owner" with password "wrongpassword"
  And There are 0 licenses registered
  When I register a license with text "text" and price "price"
  Then The response code is 401
  And The error message is "Bad credentials"
  And There are 0 licenses registered"""