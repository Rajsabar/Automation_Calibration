Feature: Calibration

Scenario: Valid login to Salesforce
    Given User the Salesforce login page
    When user enter a valid username and password from Excel "C:\Users\rajssing\Desktop\Java-Automation.xlsx" and sheet "Sheet1"
    And User click the login button
    Then user should be logged into Salesforce
    And Quoting is in progress
