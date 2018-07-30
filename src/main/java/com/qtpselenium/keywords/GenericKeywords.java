package com.qtpselenium.keywords;

import java.io.File;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

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

public class GenericKeywords {
	
	
	public Properties envProp;
	public Properties prop;
	public String objectKey;
	public String dataKey;
	
	public Hashtable<String,String> data;
	public WebDriver driver;
	
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

    /*****************************************/

	public void openBrowser(){
		String browser=data.get(dataKey);
		if(browser.equals("Mozilla")){
			//CHECK module 11************
			// options
			//System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "null");
			// invoke profile
			System.setProperty("webdriver.gecko.driver", "D:\\Common\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}else if(browser.equals("Chrome")){
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
		System.out.println(envProp.getProperty(objectKey));
		driver.get(envProp.getProperty(objectKey));
	}

	
	
	public void click(){
		//test.log(Status.INFO,"Clicking "+prop.getProperty(objectKey));
		getObject(objectKey).click();
	}
	
	
	public void type(){
		//test.log(Status.INFO,"Typing in "+prop.getProperty(objectKey)+" . Data - "+data.get(dataKey));
		getObject(objectKey).sendKeys(data.get(dataKey));
	}
	
	
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
			
		}
		
		public void takeSceenShot(){
			
		}
	
}
