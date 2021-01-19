Feature: Saving a file

  Scenario: Converting Mutlipart file to File
    Given A multipart file with content of "apple"
    When  convert it to a file
    Then  we have a file with content of "apple"

  Scenario: Saving a Multipart file to local directory
    Given A multipart file with name "temp.txt"
    When save file to local
    Then  A file with the name should exist in the local directory that is name "temp.txt"
