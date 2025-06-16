package stepDefinitions;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.ExcelUtils;
import utils.TestContainer;

import java.io.IOException;
import java.time.Duration;

public class SalesforceCalibration {

	TestContainer testContainer;
    //Actions actions = new Actions(testContainer.driver);

	public SalesforceCalibration(TestContainer testContainer) {
		this.testContainer=testContainer;
	}
	
	@Given("User the Salesforce login page")
	public void user_the_salesforce_login_page() {
	    // Write code here that turns the phrase above into concrete actions
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\rajssing\\Downloads\\chromedriver-win64 (5)\\chromedriver-win64\\chromedriver.exe");
		testContainer.driver = new ChromeDriver();
		testContainer.driver.manage().window().maximize();
		testContainer.driver.get("https://test.salesforce.com/");
	}
	
	@When("user enter a valid username and password from Excel {string} and sheet {string}")
    public void user_enter_a_valid_username_and_password_from_excel(String filePath, String sheetName) throws IOException {
        String[][] credentials = ExcelUtils.readExcelData(filePath, sheetName);

        for (String[] data : credentials) {
            String username = data[0];
            String password = data[1];

            WebElement usernameField = testContainer.driver.findElement(By.className("username"));
            usernameField.click();
            usernameField.sendKeys(username);

            WebElement passwordField = testContainer.driver.findElement(By.className("password"));
            passwordField.click();
            passwordField.sendKeys(password);
        }
    }
	@When("User click the login button")
	public void User_click_the_login_button() {
		WebElement logIn= testContainer.driver.findElement(By.id("Login"));
		logIn.click();
		testContainer.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		JavascriptExecutor js = (JavascriptExecutor)testContainer.driver;
		js.executeScript("window.scrollBy(0,250)", "");
		WebElement cancelButton= testContainer.driver.findElement(By.id("cancel-button"));
		cancelButton.click();
	}

	@Then("user should be logged into Salesforce")
	public void user_should_be_logged_into_salesforce() throws InterruptedException {
        WebElement servicesElement = testContainer.driver.findElement(By.xpath("//span[@title='Services Delivery Console']"));
		if(servicesElement.isDisplayed())
		{
			System.out.println("User has successfully logged in salesforce");
		}
				Actions actions = new Actions(testContainer.driver);
				Thread.sleep(3000);
        
	            actions.doubleClick(servicesElement).perform();
	            actions.keyDown(Keys.SHIFT).sendKeys("w").keyUp(Keys.SHIFT).perform();
	            Thread.sleep(500); // Short delay to ensure the key event is processed
	            actions.sendKeys(Keys.RETURN).perform();	
	}

}
