Feature: List Datasets order by Title
  In order to list Datasets by Title
  As a data user
  I want to list a group of datasets and order it by Title

  Scenario: List datasets
    Given There is a dataset
    And There is 1 datasets registered
    And I login as "user" with password "password"
    When I list the dataset
    Then The response code is 200

