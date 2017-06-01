Feature: Register Data Stream
  In order to contribute data to a datastream dataset
  As a data owner
  I want to upload the data contained in a local stream,
  organised as lines and using a specified string or character as field separator

  Scenario: Create a dataset by register stream
    Given I login as "owner" with password "password"
    When I register a stream with streamname "text" and title "title" and owner "owner" and separator ","
    Then The dataset contains a stream with streamname "text"
    And The datastream separator is ","