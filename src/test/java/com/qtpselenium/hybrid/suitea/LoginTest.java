package com.qtpselenium.hybrid.suitea;

import java.util.Hashtable;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.hybrid.base.BaseTest;

public class LoginTest extends BaseTest{

	
	@Test(dataProvider="getData")
	public void logintest(Hashtable<String,String> data) {
	
		System.out.println("Login Test");
		System.out.println("From Dp"+ data);
		ds.executeKeywords(testName, xls, data);
	}
	
	
	
}
