package com.qtpselenium.hybrid.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;

import com.qtpselenium.hybrid.util.Xls_Reader;

public class BaseTest {

	
	public Properties envProp;// uat or prd

	public Properties prop;//env.properties
	
	public Xls_Reader xls;
	
	
	
	@BeforeTest
	public void init() {
		
		System.out.println("Before Test");
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
		xls =new Xls_Reader(envProp.getProperty("suitea_xls"));
	}
}
