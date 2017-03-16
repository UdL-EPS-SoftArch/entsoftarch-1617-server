Feature: Share Dataset
  In order to allow others to help me manage my datasets
  As a dataset owner
  I want to share my datasets with other data owners

  Scenario: Share a previously unshared dataset
    #TODO: complete scenario, also share with multiple users, data users or owners, repeat user...

  Scenario: Dataset owner can transfer ownership
    Given There is a dataset with title "my original data" and owner "owner"
    And I login as "owner" with password "password"
    When I change the owner of dataset with title "my original data" to "owner2"
    Then The response code is 204
    And User "owner" owns 0 dataset
    And User "owner2" owns 1 dataset

  Scenario: Other users cannot change dataset owner
    Given There is a dataset with title "my original data" and owner "owner"
    And I login as "owner2" with password "password"
    When I change the owner of dataset with title "my original data" to "owner2"
    Then The response code is 403
    And The error message is "Just dataset owner can transfer ownership"
    And User "owner" owns 1 dataset
    And User "owner2" owns 0 dataset
