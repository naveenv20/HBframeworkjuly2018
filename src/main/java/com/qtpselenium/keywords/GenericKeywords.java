package com.qtpselenium.keywords;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qtpselenium.hybrid.reports.ExtentManager;

import org.testng.asserts.SoftAssert;
public class GenericKeywords {
	
	
	public Properties envProp;
	public Properties prop;
	public String objectKey;
	public String dataKey;
	
	public Hashtable<String,String> data;
	public WebDriver driver;
	public ExtentTest test;
	public String proceedOnFail;
	public SoftAssert softAssert = new SoftAssert();
	/*********************Setter functions***************************/
	
	public void setEnvProp(Properties envProp) {
		this.envProp = envProp;
	}



	public void setProp(Properties prop) {
		this.prop = prop;
	}



	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}



	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}



	public void setData(Hashtable<String, String> data) {
		this.data = data;
	}
	
	public void setExtentTest(ExtentTest test) {
		this.test = test;
	}
	
	public void setProceedOnFail(String proceedOnFail) {
		this.proceedOnFail = proceedOnFail;
	}

    /*****************************************/

	public void openBrowser(){
		
		String browser=data.get(dataKey);
		test.log(Status.INFO,"Opening Browser "+browser);
		if(browser.equals("Mozilla")){
			//CHECK module 11************
			// options
			//System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "null");
			// invoke profile
			System.setProperty("webdriver.gecko.driver", "C:\\TestingNav\\2018_sel\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}else if(browser.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", "C:\\TestingNav\\2018_sel\\drivers\\chromedriver.exe");
			// init options
			driver = new ChromeDriver();
		}else if(browser.equals("IE")){
			driver = new InternetExplorerDriver();
		}else if(browser.equals("Edge")){
			driver = new EdgeDriver();
		}
		
		// max and set implicit wait
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		
	}
	
	public void navigate(){
		test.log(Status.INFO,"Clicking "+envProp.getProperty(objectKey));
		//System.out.println(envProp.getProperty(objectKey));
		driver.get(envProp.getProperty(objectKey));
	}

	
	
	public void click(){
		test.log(Status.INFO,"Clicking "+prop.getProperty(objectKey));
		getObject(objectKey).click();
	}
	
	
	public void type(){
		test.log(Status.INFO,"Typing in "+prop.getProperty(objectKey)+" . Data - "+data.get(dataKey));
		getObject(objectKey).sendKeys(data.get(dataKey));
	}
	
	public void quit(){
		if(driver!=null)
			driver.quit();
	}
	
	
	
	// true - present
		// false - not present
		
		
		public void validateTitle(){
			test.log(Status.INFO,"Validating title - "+prop.getProperty(objectKey) );
			String expectedTitle = prop.getProperty(objectKey);
			String actualTitle=driver.getTitle();
			if(!expectedTitle.equals(actualTitle)){
				// report failure
				reportFailure("Titles did not match. Got title as "+ actualTitle);
			}
		}
		
		public void validateElementPresent(){
			if(!isElementPresent(objectKey)){
				// report failure
				reportFailure("Element not found "+objectKey);
			}
		}
		
		
		
		
		/*********************Utitlity Functions************************/
		// central function to extract Objects
		public WebElement getObject(String objectKey){
			WebElement e=null;
			try{
			if(objectKey.endsWith("_xpath"))
				e = driver.findElement(By.xpath(prop.getProperty(objectKey)));
			else if(objectKey.endsWith("_id"))
				e = driver.findElement(By.id(prop.getProperty(objectKey)));
			else if(objectKey.endsWith("_css"))
				e = driver.findElement(By.cssSelector(prop.getProperty(objectKey)));
			else if(objectKey.endsWith("_name"))
				e = driver.findElement(By.name(prop.getProperty(objectKey)));

			WebDriverWait wait = new WebDriverWait(driver,20);
			// visibility of Object
			wait.until(ExpectedConditions.visibilityOf(e));
			// state of the object-  clickable
			wait.until(ExpectedConditions.elementToBeClickable(e));
			
			}catch(Exception ex){
				// failure -  report that failure
				reportFailure("Object Not Found "+ objectKey);
			}
			return e;
			
		}
		// true - present
		// false - not present
		public boolean isElementPresent(String objectKey){
			List<WebElement> list=null;
			
			if(objectKey.endsWith("_xpath"))
				list = driver.findElements(By.xpath(prop.getProperty(objectKey)));
			else if(objectKey.endsWith("_id"))
				list = driver.findElements(By.id(prop.getProperty(objectKey)));
			else if(objectKey.endsWith("_css"))
				list = driver.findElements(By.cssSelector(prop.getProperty(objectKey)));
			else if(objectKey.endsWith("_name"))
				list = driver.findElements(By.name(prop.getProperty(objectKey)));

			if(list.size()==0)
				return false;
			else
				return true;
		}
		
		
		/*******Reporting function********/
		public void reportFailure(String failureMsg){
			
			// fail the test
			test.log(Status.FAIL, failureMsg);
			// take the screenshot, embed screenshot in reports
			takeSceenShot();
			// fail in testng
			//Assert.fail(failureMsg);// stop on this line
			if(proceedOnFail.equals("Y")){// soft assertion
				softAssert.fail(failureMsg);
			}else{
				softAssert.fail(failureMsg);
				softAssert.assertAll();
			}
		}
		
		public void takeSceenShot(){
			Date d=new Date();
			String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
			// take screenshot
			File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
				// get the dynamic folder name
				FileUtils.copyFile(srcFile, new File(ExtentManager.screenshotFolderPath+screenshotFile));
				//put screenshot file in reports
				test.log(Status.INFO,"Screenshot-> "+ test.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+screenshotFile));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void assertAll(){
			softAssert.assertAll();
		}
	
}
