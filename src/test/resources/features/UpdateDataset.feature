Feature: Update Dataset
  In order to change my owned data
  As a data owner
  I want to update a dataset

#  Scenario: Register a dataset description without data
#    Given I login as "owner" with password "password"
#    And There are 0 datasets registered
#    When I register a dataset with description "my own data"
#    Then The new dataset has description "my own data"
#    And The new dataset has date and time approximately now
#    And There is 1 dataset registered
#
#  Scenario: Register a new dataset if one already registered
#    Given I login as "owner" with password "password"
#    And There is a dataset with description "my original data" and owner "owner"
#    And There are 1 datasets registered
#    When I register a dataset with description "my own data"
#    Then There are 2 datasets registered
#
#  Scenario: Register a dataset but wrong password
#    Given I login as "owner" with password "wrongpassword"
#    And There are 0 datasets registered
#    When I register a dataset with description "my own data"
#    Then The response code is 401
#    And The error message is "Bad credentials"
#    And There are 0 datasets registered