Feature: Upload Data File
  In order to contribute data to a datafile dataset
  As a data owner
  I want to upload the data contained in a local file,
  organised as lines and using a specified string or character as field separator

  Scenario: Add file to existing advertisement
    Given I login as "owner" with password "password"
    And There is a dataset with title "my dataset" and owner "owner"
    When I upload a file with filename "test.csv"
    And I list the previous advertisement files
    Then I get a list containing 1 file
    And file number 1 has filename "overview.jpg", owner "user" and was just created