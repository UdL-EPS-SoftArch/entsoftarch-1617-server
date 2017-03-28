Feature: Comment About Dataset
  In order to comment about Dataset
  As a data user and a data owner
  I want to publish a comment about a Dataset

  Scenario: Publish a comment about Dataset with a registered user.
    Given I login as "owner" with password "password"
    And There is a dataset with title "dataset" and owner "owner"
    When I comment a dataset "dataset" with text "text" and user "owner"
    Then The new comment has been published with username "owner"
    And The new comment has date and time approximately now

  Scenario: Publish a comment about Dataset without a registered user.
    Given I'm not logged in
    And There is a dataset with title "dataset" and owner "owner"
    When I comment a dataset "dataset" with text "text"
    Then The error message is "Full authentication is required to access this resource"