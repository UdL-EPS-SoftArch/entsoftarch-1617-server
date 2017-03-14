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