Feature: Upload Data File
  In order to contribute data to a datafile dataset
  As a data owner
  I want to upload the data contained in a local file,
  organised as lines and using a specified string or character as field separator

  Scenario: Create a dataset by uploading file
    Given I login as "owner" with password "password"
    When I upload a file with filename "test.csv" and owner "owner" and title "my dataset" and separator ","
    Then The dataset contains a file with filename "test.csv"
    And The datafile content contains "test.csv" content
    And The datafile separator is ","