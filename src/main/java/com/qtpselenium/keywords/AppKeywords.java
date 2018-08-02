package com.qtpselenium.keywords;

public class AppKeywords extends GenericKeywords {

	public void validateLogin(){
	}
	
	public void defaultLogin(){
		String username=envProp.getProperty("adminusername");
		String password=envProp.getProperty("adminpassword");
		System.out.println("Default username "+username );
		System.out.println("Default password "+password );
	}
}
