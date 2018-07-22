package com.qtpselenium.hybrid.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.qtpselenium.hybrid.driver.DriverScript;
import com.qtpselenium.hybrid.util.DataUtil;
import com.qtpselenium.hybrid.util.Xls_Reader;

public class BaseTest {

	
	public Properties envProp;// uat or prd

	public Properties prop;//env.properties
	
	public Xls_Reader xls;
	
	public String testName;
	
	public DriverScript ds;
	
	
	@BeforeTest
	public void init() {
		// init testName
		System.out.println("Before Test");
		System.out.println("*** "+ this.getClass().getSimpleName());
		testName=this.getClass().getSimpleName();
		
		String arr[] = this.getClass().getPackage().getName().split("\\.");
		String suiteName= arr[arr.length-1];
		
		prop=new Properties();
		envProp= new Properties();
		try {
			FileInputStream fs= new  FileInputStream(System.getProperty("user.dir")+"//src//test//resources//env.properties");
			prop.load(fs);
			//System.out.println(prop.getProperty("env"));
			String env=prop.getProperty("env");
			fs= new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//"+env+".properties");
			envProp.load(fs);
			//System.out.println(envProp.getProperty("suitea_xls"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//initial the excel file 
		System.out.println(envProp.getProperty(suiteName+"_xls"));
		xls = new Xls_Reader(envProp.getProperty(suiteName+"_xls"));
		//xls =new Xls_Reader(envProp.getProperty("suitea_xls"));
		
		
		ds = new DriverScript();
		 ds.setEnvProp(envProp);
		 ds.setProp(prop);
	}
	
	
	
	@DataProvider
	public Object[][] getData(){
		// i can use xls file object to read data
		System.out.println("Inside data Provider");
		return DataUtil.getTestData(testName, xls);
	}
	
	
}
