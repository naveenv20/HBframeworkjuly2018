package com.qtpselenium.hybrid.suitea;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.hybrid.base.BaseTest;

public class LoginTest extends BaseTest{

	
	@Test(dataProvider="getData")
	public void logintest(String a) {
	
		System.out.println("Login Test");
		System.out.println("From Dp"+a);
	}
	
	
	@DataProvider
	public Object[][] getData(){
		System.out.println("DataProvider");
		Object[][] data= new Object[1][1];
		data[0][0]="a";
		return data;
		
		
	}
}
