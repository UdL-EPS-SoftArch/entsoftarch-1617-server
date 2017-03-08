Feature: Set Dataset License
  I want to set a license to dataset

  Scenario: Set a open license by text
    Given I login as "owner" with password "password"
    And There are 0 open licenses registered
    And There are 0 datasets registered
    When I register a open license with text "My first open license"
    Then The new open license has text "My first open license"
    And There is 1 open license registered
    When I register a dataset with title "My dataset"
    Then The new dataset has title "My dataset"
    And There is 1 dataset registered
    When I set the open license with text "My first open license" to dataset with title "My dataset"
    Then The dataset with title "My dataset" has a open license with text "My first open license"

  Scenario: Set a closed license by text and price
    Given I login as "owner" with password "password"
    And There are 0 closed licenses registered
    And There are 0 datasets registered
    When I register a closed license with text "My first closed license" and price 10
    Then The new closed license has text "My first license" and price 10
    And There is 1 closed license registered
    When I register a dataset with title "My dataset"
    Then The new dataset has title "My dataset"
    And There is 1 dataset registered
    When I set the closed license with text "My first license" and price 10 to dataset with title "My dataset"
    Then The dataset with title "My dataset" has a closed license with text "My first closed license" and price 10