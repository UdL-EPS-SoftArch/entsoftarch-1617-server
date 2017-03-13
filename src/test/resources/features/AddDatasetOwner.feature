Feature: Set a DataOwner to Dataset
  In order to set new Owner to my Dataset
  I change the owner dataset to the new owner

  Scenario: Set a new DataOwner to dataset
    Given I login as "owner" with password "password"
    And There is a dataset with title "my original data" and owner "owner"
    And There is 1 dataset registered
    When I update my dataset with title "my original data" to new owner "owner2"
    Then User "owner2" owns 1 dataset
    And There is 1 dataset registered
