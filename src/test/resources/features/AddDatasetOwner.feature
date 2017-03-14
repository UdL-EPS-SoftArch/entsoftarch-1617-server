Feature: Share Dataset
  In order to share my Dataset
  I add a new DataOwner to sharedWith

  Scenario: Set a new DataOwner to sharedWith
    Given I login as "owner2" with password "password"
    And There is a dataset with title "my original data" and owner "owner"
    And There is 1 dataset registered
    When I update my dataset with title "my original data" to new owner "owner2"
    Then User "owner2" owns 1 dataset
    And There is 1 dataset registered
