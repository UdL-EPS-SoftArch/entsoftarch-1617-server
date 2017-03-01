Feature: Delete Dataset
  In order to stop sharing or selling owned data
  As a data owner
  I want to deregister a dataset and make it unavailable

  Scenario: Delete owned dataset
    Given There is a dataset with title "dataset owned by owner" and owner "owner"
    And There is 1 datasets registered
    And I login as "owner" with password "password"
    When I delete the dataset with title "dataset owned by owner"
    Then The response code is 204
    And There are 0 datasets registered

  Scenario: Delete unowned dataset
    Given There is a dataset with title "dataset owned by owner" and owner "owner"
    And I login as "user" with password "password"
    When I delete the dataset with title "dataset owned by owner"
    Then The response code is 403
    And The error message is "Access is denied"