package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class DropdownHelper {
    public static void selectFromDropdown(TestContainer testContainer, String fieldName, String inputFieldIdPrefix) {
        String inputFieldId = "";
        int counter = 0;
        int maxRetries = 5;

        while (inputFieldId.isEmpty() && counter < maxRetries) {
            try {
                Thread.sleep(2000); // Wait for 2 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Field Name: " + fieldName);

            try {
                // Locate the label
                WebElement label = testContainer.driver.findElement(By.xpath("//label[text()='" + fieldName + "']"));
                String labelAttr = label.getAttribute("for");

                System.out.println("Label Attribute: " + labelAttr);

                // If labelAttr is empty, look for alternative label structure
                if (labelAttr == null || labelAttr.isEmpty()) {
                    label = testContainer.driver.findElement(By.xpath("//label[text()='" + fieldName + "']//span//parent::label"));
                    labelAttr = label.getAttribute("for");
                    System.out.println("Alternative Label Attribute: " + labelAttr);
                }

                // Extract the last part of the attribute
                if (labelAttr != null && !labelAttr.isEmpty()) {
                    String[] list = labelAttr.split("-");
                    inputFieldId = list[list.length - 1];
                    System.out.println("Input Field ID: " + inputFieldId);
                }
            } catch (Exception e) {
                System.out.println("An exception occurred while locating the label: " + e.getMessage());
            }

            counter++;
        }

        if (inputFieldId.isEmpty()) {
            System.out.println("Failed to find a valid inputFieldId after " + maxRetries + " attempts.");
            return;
        }

        try {
            // Locate the input field using the generated inputFieldId
            WebElement inputField = testContainer.driver.findElement(By.xpath("//input[@id='" + inputFieldIdPrefix + inputFieldId + "']"));
            JavascriptExecutor js = (JavascriptExecutor) testContainer.driver;

            js.executeScript("arguments[0].click();", inputField);

            WebElement element = testContainer.driver.findElement(By.xpath("//div[@id='listbox-id-3-" + inputFieldId + "']//ul//li"));
            js.executeScript("arguments[0].click();", element);

            // Fire 'blur' or 'change' event
            js.executeScript("arguments[0].dispatchEvent(new Event('blur'));", element);
            js.executeScript("arguments[0].dispatchEvent(new Event('change'));", element);

            // Force close the dropdown
            js.executeScript("arguments[0].style.display = 'none';", element);
            js.executeScript("document.body.click();");
        } catch (Exception e) {
            System.out.println("An exception occurred while interacting with the dropdown: " + e.getMessage());
        }
    }
}
