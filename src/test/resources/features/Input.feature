Feature: Input Tests

  Scenario: I have a chat log with 3 participants and these can be listed with detectChatterNames
    Given the following path: "src/test/resources/ChatTest.txt"

    When the chatter names are detected

    Then there are 3 people listed
