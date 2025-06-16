package stepDefinitions;
import io.cucumber.java.Scenario;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;


import org.apache.commons.io.FileUtils;

//import com.aventstack.extentreports.gherkin.model.Scenario;
//import com.mongodb.MapReduceCommand.OutputType;

import io.cucumber.java.AfterStep;
import utils.TestContainer;

public class Hooks {
	 TestContainer testContainer;

	    public Hooks(TestContainer testContainer) {
	        this.testContainer = testContainer;
	    }
//	    @After
//	    public void AfterScenario() {
//	    	testContainer.testBase.WebDriverManager().quit();
//	    }
	    @AfterStep
	    public void AddScreenshot(Scenario scenario) throws IOException {
	    	if (scenario.isFailed()) {
	    		File screenshot = ((TakesScreenshot) testContainer.driver).getScreenshotAs(OutputType.FILE);
	    		byte[] screenshotBytes = FileUtils.readFileToByteArray(screenshot);
	    		scenario.attach(screenshotBytes, "image/png", "Failure Screenshot");

	        }
	    }

}
