package com.qtpselenium.keywords;

import com.aventstack.extentreports.Status;

public class AppKeywords extends GenericKeywords {

	public void validateLogin(){
		test.log(Status.INFO, "Inside the validate login ");
	}
	
	public void defaultLogin(){
		String username=envProp.getProperty("adminusername");
		String password=envProp.getProperty("adminpassword");
		System.out.println("Default username "+username );
		System.out.println("Default password "+password );
	}
}
