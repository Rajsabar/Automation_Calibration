Feature: Calibration

Scenario: Valid login to Salesforce
    Given User the Salesforce login page
    When user enter a valid username and password from Excel "C:\Users\rajssing\Desktop\Java-Automation.xlsx" and sheet "Sheet1"
    And User click the login button
    Then user should be logged into Salesforce
    And Search the asset
    Then Check open WO
    Then Check product support strategy
    And Check the all product Support strategy
    Then Create a work order
    Then Verify workflow status codes are correct
    And Pricing process is done
    Then Create a new quote
    And Quoting is in progress
