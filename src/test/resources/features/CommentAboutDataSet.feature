Feature: Comment About Dataset
  In order to comment about Dataset
  As a data user and a data owner
  I want to publish a comment about a Dataset

  Scenario: Publish a comment about Dataset with a registered user.
    Given I login as "owner" with password "password"
    And There are 1 datasets registered
    When I comment a dataset
    Then This dataset has a new comment
    And The new comment has been published with username "owner" or username "user"
    And The new comment has date and time approximately now

  Scenario: Publish a comment about Dataset without a registered user.
    Given User not registered
    And There are 1 datasets registered
    When I comment a dataset
    Then Return error(user not registered)