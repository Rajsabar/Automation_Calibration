package stepDefinitions;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import utils.DropdownHelper;
import utils.TestContainer;
import utils.XPathHelper;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class LoginClaibration {
    TestContainer testContainer;

    public LoginClaibration(TestContainer testContainer) {
        this.testContainer = testContainer;
    }

    @And("Search the asset")
    public void Search_the_asset() throws InterruptedException {
        testContainer.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        waitForElement(5000);
        clickElement(By.xpath(XPathHelper.SEARCH_BUTTON));
        Actions actions = new Actions(testContainer.driver);
        String inputText = "WO-01105209";

        try {
            WebElement searchBox1 = findElement(By.xpath(XPathHelper.SEARCH_BOX_1));
            sendKeysToElement(searchBox1, inputText);
            System.out.println("Input sent to the first search box.");
        } catch (NoSuchElementException e) {
            WebElement searchBox2 = findElement(By.xpath(XPathHelper.SEARCH_BOX_2));
            sendKeysToElement(searchBox2, inputText);
            System.out.println("Input sent to the second search box.");
        }

        waitForElement(10000);
        actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
    }

    @Then("Check open WO")
    public void Check_open_WO() {
        List<WebElement> assetOpenList = findElements(By.xpath(XPathHelper.ASSET_OPEN_WORKORDER));
        System.out.println(assetOpenList.size());

        if (assetOpenList.isEmpty()) {
            System.out.println("Asset has no Work Order. Test passed.");
        } else if (assetOpenList.get(0).isDisplayed()) {
            throw new AssertionError("Asset is in Open WorkOrder state, test failed.");
        }
    }

    @Then("Check product support strategy")
    public void Check_product_support_strategy() throws InterruptedException {
        scrollTo(0, 1250);
        waitForElement(1000);
        clickElement(By.xpath(XPathHelper.PRODUCT_SUPPORT_PARENT_PRODUCT));

        Actions actions = new Actions(testContainer.driver);
        for (int i = 0; i < 9; i++) {
            actions.sendKeys(Keys.ARROW_DOWN).perform();
        }

        WebElement element = findElement(By.xpath(XPathHelper.VIEW_ALL_PRODUCT_SUPPORT));
        clickElementUsingJS(element);
    }

    @And("Check product Support strategy")
    public void Check_product_Support_strategy() {
        boolean desiredValue = false;
        try {
            List<String> requiredList = new ArrayList<>();
            List<WebElement> listOfTableData = findElements(By.xpath(XPathHelper.SERVICE_TABLE_DATA));
            for (WebElement tableData : listOfTableData) {
                requiredList.add(tableData.getText());
                System.out.println(tableData.getText());

                if (tableData.getText().equalsIgnoreCase("Calibration")) {
                    desiredValue = true;
                }
            }
        } catch (Exception e) {
            System.out.println("An exception occurred: " + e.getMessage());
        }

        if (desiredValue) {
            System.out.println("Value is present");
        }

        Actions actions = new Actions(testContainer.driver);
        actions.sendKeys(Keys.ARROW_LEFT).perform();
        scrollToTop();
    }

    @Then("Create a work order")
    public void Create_a_work_order() throws InterruptedException {
        clickElement(By.xpath(XPathHelper.CREATE_WORKORDER_BUTTON));
        waitForElement(10000);

        clickElement(By.xpath(XPathHelper.NEW_WORKORDER_DIALOG));
        waitForElement(10000);

        scrollTo(0, 400);
        DropdownHelper.selectFromDropdown(testContainer, "Bill To Contact", "customLookUP-");
        DropdownHelper.selectFromDropdown(testContainer, "Ship To Contact", "customLookUP-");

        scrollTo(0, 600);
        WebElement serviceBundleInput = findElement(By.xpath(XPathHelper.SERVICE_BUNDLE_BUTTON));
        clickElement(serviceBundleInput);
        
        
        WebElement serviceBundleSearch = findElement(By.xpath(XPathHelper.SERVICE_BUNDLE_SEARCH));
        serviceBundleSearch.sendKeys("Calibration Only");
        WebElement serviceBundle = findElement(By.xpath(XPathHelper.SERVICE_BUNDLE_ITEM));
        serviceBundle.click();
        scrollTo(0, 150);
        
        WebElement date = findElement(By.xpath(XPathHelper.EXPECTED_RECEIPT_DATE));
        date.click();
        date.click();
        Thread.sleep(4);
        performTabNavigation(5);

        Thread.sleep(2);
        WebElement receivingMethodInput = findElement(By.xpath(XPathHelper.RECEIVING_METHOD_BUTTON));
        receivingMethodInput.click();
        performDropdownSelection(3);
        Thread.sleep(10);
        WebElement tradeButton = findElement(By.xpath(XPathHelper.TRADE_BUTTON));
        scrollTo(0, 500);
        clickElement(tradeButton);

        scrollTo(0, 500);
        Thread.sleep(2);
        clickElement(By.xpath(XPathHelper.SAVE_BUTTON));
        Thread.sleep(60);
    }
    @Then("Verify workflow status codes are correct")
    public void Verify_workflow_status_codes_are_correct() throws InterruptedException
    {
    	 WebElement servicesElement = testContainer.driver.findElement(By.xpath("//span[@title='Services Delivery Console']"));
 		if(servicesElement.isDisplayed())
 		{
 			System.out.println("User is on home page with an work order");
 		}
 		Thread.sleep(60);
    	WebElement workOrderServices = findElement(By.xpath(XPathHelper.WORK_ORDER_SERVICES));
    	workOrderServices.click();
    	List<String> requiredList = new ArrayList<>();
        List<WebElement> requiredWorkOrderList = findElements(By.xpath("//section[@aria-expanded='true']//section[@aria-expanded='true']//table//tr//th[@data-label='Work Order Service Name']"));
        
        for (WebElement tableData : requiredWorkOrderList) {
            requiredList.add(tableData.getText());
            System.out.println(tableData.getText());

        }
        WebElement receiving = findElement(By.xpath("//button[@name='SD_Work_Order__c.Receiving']"));
        receiving.click();
//        WebElement okButton =findElement(By.xpath("//span[contains(text(),'OK')]"));
//        if(okButton.isDisplayed())
//        {
//        	okButton.click();
//          okButton.click();
//        }
        List<WebElement> okButtons = findElements(By.xpath("//span[contains(text(),'OK')]"));
        if (!okButtons.isEmpty() && okButtons.get(0).isDisplayed()) {
            okButtons.get(0).click();
            // If needed, you can click again or remove the second click
            // okButtons.get(0).click();
        }

        
        WebElement country = findElement(By.xpath("//input[@name='countryOfOrigin']"));
        country.click();
        country.sendKeys("US");
     // Locate the dropdown using name or XPath
        WebElement safetyResult = findElement(By.xpath("//select[@name='safetyResult']"));

        // Create Select object
        Select safetyResultDropdown = new Select(safetyResult);

        // Select by visible text, e.g., "Passed"
        safetyResultDropdown.selectByVisibleText("Passed");
        
        WebElement visualInspection =findElement(By.xpath("//select[@name='visualInspection']"));
        Select safetyResultDropdown2 = new Select(visualInspection);
        safetyResultDropdown2.selectByVisibleText("Passed");
        
        WebElement courier =findElement(By.xpath("//select[@name='Courier']"));
        Select safetyResultDropdown3 = new Select(courier);
        safetyResultDropdown3.selectByVisibleText("Keysight Shuttle");
        
      
        WebElement inputField = findElement(By.xpath("//input[@name='trackNumber']"));
        inputField.sendKeys("123456");
     // Get the value of the 'id' attribute
//     String inputId = inputField.getAttribute("id");
//     System.out.println("The dynamic ID is: " + inputId);
        
        WebElement accessiores = findElement(By.xpath("//textarea[@name='accesories']"));
        accessiores.sendKeys("Test1233");
        
       WebElement receiveButton = findElement(By.xpath("//button[contains(text(),'Receive')]"));
       receiveButton.click();
       
       WebElement NoButton = findElement(By.xpath("//button[@title='No']"));
       NoButton.click();

     
        
    }
    
    @And("Pricing process is done")
    public void Pricing_process_is_done() throws InterruptedException
    {
    	WebElement pricingTab = findElement(By.xpath("//a[@data-label='Pricing']"));
    	pricingTab.click();
    	
    	WebElement moreActions= findElement(By.xpath("//span[contains(text(),'Show more actions')]"));
    	JavascriptExecutor js = (JavascriptExecutor) testContainer.driver;
    	js.executeScript("arguments[0].click();", moreActions);
    	
    	WebElement autoPrice=findElement(By.xpath("//runtime_platform_actions-action-renderer[@title='Auto Price']"));
//    	js.executeScript("arguments[0].click();", autoPrice);
//    	
//    	WebElement element = findElement(By.xpath("//runtime_platform_actions-action-renderer[@title='Edit']"));
    	((JavascriptExecutor) testContainer.driver).executeScript("arguments[0].scrollIntoView(true);", autoPrice);
    	autoPrice.click();
    	
    	
    	
    	
    	WebElement viewAll = findElement(By.xpath("//a[contains(text(),'View All')]"));  	
    	js.executeScript("arguments[0].click();", viewAll);
    	
//    	WebElement SFIToggle= findElement(By.xpath("//span[text()='Filter For SFI']"));
//    	SFIToggle.click();
    	
    	WebDriverWait wait = new WebDriverWait(testContainer.driver, Duration.ofSeconds(10));
    	WebElement selectAll = wait.until(ExpectedConditions.elementToBeClickable(
    	    By.xpath("//span[text()='Filter For SFI']/ancestor::article/following-sibling::div//span[text()='Select All']/parent::label")
    	));
    	selectAll.click();

    	
    	WebElement saveAndPrice= findElement(By.xpath("//span[text()='Filter For SFI']/ancestor::article/ancestor::article//div//header//button[@title='Save and Price']"));
    	saveAndPrice.click();
    	
    	List<WebElement> elements = findElements(By.xpath(
    		    "//section[@aria-expanded='true']//section[@aria-expanded='true']//span[text()='Last Priced On']" +
    		    "//ancestor::lightning-output-field//div//lightning-formatted-text"
    		));

    		if (!elements.isEmpty()) {
    		    String text = elements.get(0).getText();
    		    System.out.println("Text is: " + text);
    		} else {
    		    System.out.println("Element does not exist.");
    		}
     //Closing the tab
    		WebElement actionButton = findElement(By.xpath("//div[@title='Actions for Pricing']//button//lightning-primitive-icon"));
    		actionButton.click();
    		WebElement closeTab =findElement(By.xpath("//li[contains(@class,'tabItem')]//button[contains(@title,'Actions for') and contains(@title,'Pricing')]//parent::*//li[@title='Close Tab']//a"));
    		closeTab.click();
    	
    
    	
    	
    	
////    	WebElement editIcon = findElement(By.xpath("//lightning-primitive-cell-factory[@data-label='Charge Code/Labor Item']//div//lightning-primitive-icon[@exportparts='icon']"));
////    	editIcon.sendKeys("R-50G-511");
//    	
//    	WebElement element = findElement(By.xpath("//lightning-primitive-cell-factory[@data-label='Charge Code/Labor Item']//div//lightning-primitive-icon[@exportparts='icon']"));
//    	((JavascriptExecutor) testContainer.driver).executeScript("arguments[0].scrollIntoView(true);", element);
//    	element.click();
    	
    	
    }
    @Then("Create a new quote")
    public void create_a_new_quote() {
    	WebElement pricingTab = findElement(By.xpath("//a[@data-label='Pricing']"));
    	pricingTab.click();
    	
    	//Scroll down the page
    	scrollTo(0,700);
    	
    	
    	
    	WebDriverWait wait = new WebDriverWait(testContainer.driver, Duration.ofSeconds(15));
    	WebElement newQuote =findElement(By.xpath("//button[text()='New Quote']"));
    	newQuote.click();
    	
    	By woLink = By.xpath("//span[@class='toastMessage forceActionsText']//a");

    	WebElement openWOPage = wait.until(ExpectedConditions.elementToBeClickable(woLink));
    	openWOPage.click();

    }
    @And("Quoting is in progress")
    public void Quoting_is_in_progress() {
    	// Clicking on the pricing
//    	WebElement pricingButtonElement =findElement(By.xpath("//button[@name='SD_Quote__c.Price']"));
//    	pricingButtonElement.click();
    	
    	//Scroll down the page
    	scrollTo(0,600);
    	
    	// Wait for the pricing amount to appear (wait up to 15 seconds)
    	WebDriverWait wait = new WebDriverWait(testContainer.driver, Duration.ofSeconds(15));
    	WebElement priceValue = wait.until(ExpectedConditions.visibilityOfElementLocated(
    	    By.xpath("//span[text()='Total List Amount']/ancestor::record_flexipage-record-field//span//slot//lightning-formatted-number")
    	));
    	//WebElement priceValue =findElement(By.xpath("//span[text()='Total List Amount']/ancestor::record_flexipage-record-field//span//slot//lightning-formatted-number"));
    	// Get the text once it is visible
    	String totalListAmount = priceValue.getText();
    	System.out.println("Total List Amount: " + totalListAmount);
    	
    	//Scroll to the top of the page
    	((JavascriptExecutor) testContainer.driver).executeScript("window.scrollTo(0, 0);");

    	
    	//Submit for Approval
    	
    	//WebElement submitForApproval = testContainer.driver.findElement(By.xpath("//button[@name='SD_Quote__c.Submit_for_Approval']"));
    //	((JavascriptExecutor) testContainer.driver).executeScript("arguments[0].click();", submitForApproval);
    	
    	WebElement status= findElement(By.xpath("//span[text()='Quote Status']/ancestor::record_flexipage-record-field//span//slot//lightning-formatted-text"));
    	String quoteStatus = status.getText();
    	System.out.println(quoteStatus);
    	
    	if ("Pending Approval".equals(quoteStatus)) 
    	{
    		// Go to approvals and approve the quote
    		WebElement approvalTab= findElement(By.xpath("//a[@data-label='Approvals']"));
    		approvalTab.click();
    		
    		WebDriverWait element = new WebDriverWait(testContainer.driver, Duration.ofSeconds(10));
    		WebElement approveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@title='Approve']")));
    		approveButton.click();

    		//Click on Approve button
//    		WebElement approveButton = findElement(By.xpath("//div[@title='Approve']"));
//    		approveButton.click();
    		WebElement approve=findElement(By.xpath("//span[text()='Approve']"));
    		approve.click();
    		
    	}
    	
    	// Click on calculate tax Button   	
    	WebElement calculateTax = testContainer.driver.findElement(By.xpath("//button[@name='SD_Quote__c.Calculate_Tax']"));
    	((JavascriptExecutor) testContainer.driver).executeScript("arguments[0].click();", calculateTax);

    	
    	
    }

    
    
    
    
    
    
    
    
   
    
    // Utility Methods
    private void waitForElement(int milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }

    private WebElement findElement(By locator) {
        return testContainer.driver.findElement(locator);
    }

    private List<WebElement> findElements(By locator) {
        return testContainer.driver.findElements(locator);
    }

    private void clickElement(By locator) {
        findElement(locator).click();
    }

    private void clickElement(WebElement element) {
        element.click();
    }

    private void sendKeysToElement(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    private void scrollTo(int x, int y) {
        JavascriptExecutor js = (JavascriptExecutor) testContainer.driver;
        js.executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
    }

    private void scrollToTop() {
        ((JavascriptExecutor) testContainer.driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
    }

    private void clickElementUsingJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) testContainer.driver;
        js.executeScript("arguments[0].click();", element);
    }

    private void performDropdownSelection(int downPresses) {
        Actions actions = new Actions(testContainer.driver);
        for (int i = 0; i < downPresses; i++) {
            actions.sendKeys(Keys.ARROW_DOWN).perform();
        }
        actions.sendKeys(Keys.RETURN).perform();
    }

    private void performTabNavigation(int tabs) {
        Actions actions = new Actions(testContainer.driver);
        for (int i = 0; i < tabs; i++) {
            actions.sendKeys(Keys.TAB).perform();
        }
        actions.sendKeys(Keys.RETURN).perform();
    }
}
