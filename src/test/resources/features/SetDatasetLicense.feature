Feature: Set Dataset License
  I want to set a license to dataset

  Scenario: Set a open license by text
    Given I login as "owner" with password "password"
    And I register a open license with text "My first open license"
    And There is a dataset with title "My dataset" and owner "owner"
    When I set the open license with text "My first open license" to dataset with title "My dataset"
    Then The dataset with title "My dataset" has a open license with text "My first open license"

  Scenario: Set a closed license by text and price
    Given I login as "owner" with password "password"
    And I register a closed license with text "My first closed license" and price 10
    And There is a dataset with title "My dataset" and owner "owner"
    When I set the closed license with text "My first closed license" and price 10 to dataset with title "My dataset"
    Then The dataset with title "My dataset" has a closed license with text "My first closed license" and price 10

  Scenario: Set another open license to the same dataset
    Given I login as "owner" with password "password"
    And There is a dataset with title "My dataset" and owner "owner"
    And There is a open license with text "My first open license" and owner "owner"
    When I set the open license with text "My first open license" to dataset with title "My dataset"
    And There is a open license with text "My second open license" and owner "owner"
    When I set the open license with text "My second open license" to dataset with title "My dataset"
    And The datasets defined by open license with text "My second open license" include one titled "My dataset"
    And The open license with text "My first open license" don't include a dataset with title "My dataset"

  Scenario: Set two datasets to the same open license
    Given I login as "owner" with password "password"
    And There is a dataset with title "My dataset" and owner "owner"
    And There is a dataset with title "My other dataset" and owner "owner"
    And There is a open license with text "My first open license" and owner "owner"
    When I set the open license with text "My first open license" to dataset with title "My dataset"
    When I set the open license with text "My first open license" to dataset with title "My other dataset"
    And The datasets defined by open license with text "My first open license" include one titled "My dataset"
    And The datasets defined by open license with text "My first open license" include one titled "My other dataset"
    Then The open license with text "My first open license" has 2 datasets registered

  Scenario: Set another closed license to the same dataset
    Given I login as "owner" with password "password"
    And There is a dataset with title "My dataset" and owner "owner"
    And There is a closed license with text "My first closed license" and price 10 and owner "owner"
    When I set the closed license with text "My first closed license" and price 10 to dataset with title "My dataset"
    And There is a closed license with text "My second closed license" and price 10 and owner "owner"
    When I set the closed license with text "My second closed license" and price 5 to dataset with title "My dataset"
    And The datasets defined by closed license with text "My second closed license" and price 5 include one titled "My dataset"
    And The closed license with text "My first closed license" and price 10 don't include a dataset with title "My dataset"

  Scenario: Set two datasets to the same closed license
    Given I login as "owner" with password "password"
    And There is a dataset with title "My dataset" and owner "owner"
    And There is a dataset with title "My other dataset" and owner "owner"
    And There is a closed license with text "My first closed license" and price 10 and owner "owner"
    When I set the closed license with text "My first closed license" and price 10 to dataset with title "My dataset"
    When I set the closed license with text "My first closed license" and price 10 to dataset with title "My other dataset"
    And The datasets defined by closed license with text "My first closed license" and price 10 include one titled "My dataset"
    And The datasets defined by closed license with text "My first closed license" and price 10 include one titled "My other dataset"
    Then The closed license with text "My first closed license" and price 10 has 2 datasets registered