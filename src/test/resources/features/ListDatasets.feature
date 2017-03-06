Feature: List Datasets order by Title
  In order to list Datasets by Title
  As a data user
  I want to list a group of datasets and order it by Title

  Scenario: List datasets
    Given There is a dataset with title "anyTest" and owner "owner"
    And There is 1 datasets registered
    And I login as "user" with password "password"
    When I list the dataset
    Then The response code is 200
    And User "owner" owns 1 dataset

  Scenario: List ordered datasets and order by Title
    Given There is a dataset with title "anyTest" and owner "owner"
    And There is a dataset with title "anyTest1" and owner "owner"
    And There is 2 datasets registered
    And I login as "user" with password "password"
    When I list the dataset order by title
    Then The response code is 200
    And In the position 0 there is a dataset with title "anyTest"
    And In the position 1 there is a dataset with title "anyTest1"
    And User "owner" owns 2 dataset

  Scenario: List not ordered datasets and order by Title
    Given There is a dataset with title "anyTest1" and owner "owner"
    And There is a dataset with title "anyTest" and owner "owner"
    And There is 2 datasets registered
    And I login as "user" with password "password"
    When I list the dataset order by title
    Then The response code is 200
    And In the position 0 there is a dataset with title "anyTest"
    And In the position 1 there is a dataset with title "anyTest1"
    And User "owner" owns 2 dataset

  Scenario: List datasets not order by Title
    Given There is a dataset with title "anyTest1" and owner "owner"
    And There is a dataset with title "anyTest" and owner "owner"
    And There is 2 datasets registered
    And I login as "user" with password "password"
    When I list the dataset
    Then The response code is 200
    And In the position 1 there is a dataset with title "anyTest"
    And In the position 0 there is a dataset with title "anyTest1"
    And User "owner" owns 2 dataset


